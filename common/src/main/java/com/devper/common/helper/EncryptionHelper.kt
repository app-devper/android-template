package com.devper.common.helper

import android.annotation.TargetApi
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.math.BigInteger
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.security.auth.x500.X500Principal
import android.security.KeyPairGeneratorSpec as KeyPairGeneratorSpec1

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
class EncryptionHelper constructor(mContext: Context, keyName: String) {

    private var mKeyName = "KEY_NAME"
    private lateinit var keyStore: KeyStore
    private val mPrefsHelper: SharedPreferences
    private var appName: String

    init {
        mKeyName = keyName
        mPrefsHelper = mContext.getSharedPreferences(ENCRYPTED_PREF, Context.MODE_PRIVATE)
        val applicationInfo = mContext.applicationInfo
        val stringId = applicationInfo.labelRes
        appName =  if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else mContext.getString(stringId)
    }

    fun encryptKey(context: Context): ByteArray
        {
            var encryptedKey = ByteArray(64)
            try {
                keyStore = KeyStore.getInstance(ANDROID_KEY_STORE)
                keyStore.load(null)

                Log.d("Encryption", "Not containsAlias: " + !keyStore.containsAlias(appName))
                if (!keyStore.containsAlias(appName)) {
                    val kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, ANDROID_KEY_STORE)
                    if (IS_M) {
                        val spec = KeyGenParameterSpec.Builder(appName, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                            .setRandomizedEncryptionRequired(false)
                            .build()
                        kpg.initialize(spec)
                    } else {
                        val start = Calendar.getInstance()
                        val end = Calendar.getInstance()
                        end.add(Calendar.YEAR, 30)
                        val spec = KeyPairGeneratorSpec1.Builder(context)
                            .setAlias(appName)
                            .setSubject(X500Principal("CN=$appName"))
                            .setSerialNumber(BigInteger.TEN)
                            .setStartDate(start.time)
                            .setEndDate(end.time)
                            .build()
                        kpg.initialize(spec)
                    }
                    kpg.generateKeyPair()
                    encryptedKey = generate64BitSecretKey()
                } else {
                    encryptedKey = secretKey
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.d("Encryption", "encryptedKey: " + encryptedKey.size + " -- " + Arrays.toString(encryptedKey))
            return encryptedKey
        }

    private val secretKey: ByteArray
        get() {
            val encryptedKeyB64 = mPrefsHelper.getString(ENCRYPTED_KEY, null)
            var key = ByteArray(64)
            try {
                val encryptedKey = Base64.decode(encryptedKeyB64, Base64.DEFAULT)
                key = rsaDecrypt(encryptedKey)

            } catch (e: Exception) {
                e.printStackTrace()
            }

            Log.d("Encryption", "getSecretKey string: " + encryptedKeyB64!!)
            Log.d("Encryption", "getSecretKey key: " + Arrays.toString(key))
            return key
        }

    private fun generate64BitSecretKey(): ByteArray {
        val key = ByteArray(64)
        try {
            var encryptedKeyB64 = mPrefsHelper.getString(ENCRYPTED_KEY, null)
            if (encryptedKeyB64 == null) {
                val secureRandom = SecureRandom()
                secureRandom.nextBytes(key)
                val encryptedKey = rsaEncrypt(key)
                encryptedKeyB64 = Base64.encodeToString(encryptedKey, Base64.DEFAULT)
                mPrefsHelper.edit().putString(ENCRYPTED_KEY, encryptedKeyB64).apply()
                Log.d("Encryption", "generate64BitSecretKey string: " + encryptedKeyB64!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        Log.d("Encryption", "generate64BitSecretKey key: " + Arrays.toString(key))
        return key
    }

    @Throws(Exception::class)
    private fun rsaEncrypt(secret: ByteArray): ByteArray {
        val privateKeyEntry = keyStore.getEntry(mKeyName, null) as KeyStore.PrivateKeyEntry
        val inputCipher = Cipher.getInstance(RSA_MODE)
        inputCipher.init(Cipher.ENCRYPT_MODE, privateKeyEntry.certificate.publicKey)

        val outputStream = ByteArrayOutputStream()
        val cipherOutputStream = CipherOutputStream(outputStream, inputCipher)
        cipherOutputStream.write(secret)
        cipherOutputStream.close()
        return outputStream.toByteArray()
    }

    @Throws(Exception::class)
    private fun rsaDecrypt(encrypted: ByteArray): ByteArray {
        val privateKeyEntry = keyStore.getEntry(mKeyName, null) as KeyStore.PrivateKeyEntry
        val output = Cipher.getInstance(RSA_MODE)
        output.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)
        val cipherInputStream = CipherInputStream(ByteArrayInputStream(encrypted), output)
        val values = ArrayList<Byte>()
        var nextByte = cipherInputStream.read()
        while (nextByte != -1) {
            values.add(nextByte.toByte())
            nextByte = cipherInputStream.read()
        }

        val bytes = ByteArray(values.size)
        for (i in bytes.indices) {
            bytes[i] = values[i]
        }
        return bytes
    }

    companion object {

        private val IS_M = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
        private const val RSA_MODE = "RSA/ECB/PKCS1Padding"
        private const val ENCRYPTED_KEY = "ENCRYPTED_KEY"
        private const val ENCRYPTED_PREF = "ENCRYPTED_PREF"

        private var instance: EncryptionHelper? = null

        fun initHelper(context: Context, keyName: String): EncryptionHelper {
            if (instance == null) {
                instance = EncryptionHelper(context, keyName)
            }
            return instance!!
        }

        fun getInstance(): EncryptionHelper {
            if (instance == null) {
                throw NullPointerException("Null instance. You must call initHelper() before using.")
            }
            return instance!!
        }
    }
}