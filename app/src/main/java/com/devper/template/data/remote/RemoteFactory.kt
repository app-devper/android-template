package com.devper.template.data.remote

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.devper.template.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class RemoteFactory(private val context: Context) {

    fun createOkHttpClient(interceptor: HttpInterceptor): OkHttpClient = OkHttpClient.Builder().run {
        addInterceptor(interceptor)
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        readTimeout(30, TimeUnit.SECONDS)
        connectTimeout(30, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        build()
    }

    inline fun <reified T> createApiService(okHttpClient: OkHttpClient, url: String): T {
        val retrofit = Retrofit.Builder().run {
            baseUrl(url)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
            build()
        }
        return retrofit.create(T::class.java)
    }

    private fun certificationPining(): CertificatePinner {
        return CertificatePinner.Builder().run {
            add("common-api-app.herokuapp.com", "sha256/bGtoiyP3xfIonaf84WYToG0fh7ntDEu/Kjr4FdFjrSM=")
            build()
        }
    }

    private fun getSslSocketFactoryForCertificateFile(fileName: String): Pair<SSLSocketFactory, X509TrustManager>? {
        try {
            val keyStore = getKeyStore(fileName)
            val sslContext = SSLContext.getInstance("SSL")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            val trustManager = trustManagerFactory.trustManagers[0] as X509TrustManager
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())
            return Pair(sslContext.socketFactory, trustManager)
        } catch (e: Exception) {
            val msg = "Error during creating SslContext for certificate from assets"
            e.printStackTrace()
            throw RuntimeException(msg)
        }
    }

    @SuppressLint("LogNotTimber")
    private fun getKeyStore(fileName: String): KeyStore? {
        var keyStore: KeyStore? = null
        try {
            val assetManager = context.assets
            val cf = CertificateFactory.getInstance("X.509")
            val caInput = assetManager.open(fileName)
            val ca = caInput.run {
                cf.generateCertificate(caInput)
            }
            Log.d("SslUtilsAndroid", "ca=" + (ca as X509Certificate).subjectDN)
            val keyStoreType = KeyStore.getDefaultType()
            keyStore = KeyStore.getInstance(keyStoreType)
            keyStore?.load(null, null)
            keyStore.setCertificateEntry("ca", ca)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return keyStore
    }

    private fun createGson(): Gson = GsonBuilder()
        .setLenient()
        .create()

    @SuppressLint("TrustAllX509TrustManager")
    private fun getTrustAllHostsSSLSocketFactory(): Pair<SSLSocketFactory, X509TrustManager>? {
        try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }

                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
                }
            })
            val trustManager = trustAllCerts[0] as X509TrustManager
            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())
            // Create an ssl socket factory with our all-trusting manager
            return Pair(sslContext.socketFactory, trustManager)
        } catch (e: KeyManagementException) {
            e.printStackTrace()
            return null
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return null
        }

    }

}


