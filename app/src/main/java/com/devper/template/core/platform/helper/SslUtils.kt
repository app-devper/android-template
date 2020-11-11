package com.devper.template.core.platform.helper

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.*

object SslUtils {

    fun getSslSocketFactoryForCertificateFile(context: Context, fileName: String): Pair<SSLSocketFactory, X509TrustManager>? {
        try {
            val keyStore = getKeyStore(context, fileName)
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
    private fun getKeyStore(context: Context, fileName: String): KeyStore? {
        val assetManager = context.assets
        val cf = CertificateFactory.getInstance("X.509")
        val caInput = assetManager.open(fileName)
        val ca = caInput.run {
            cf.generateCertificate(caInput)
        }
        Log.d("SslUtilsAndroid", "ca=" + (ca as X509Certificate).subjectDN)
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore: KeyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)
        return keyStore
    }

    @SuppressLint("TrustAllX509TrustManager")
    fun getTrustAllHostsSSLSocketFactory(): Pair<SSLSocketFactory, X509TrustManager>? {
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