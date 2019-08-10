package com.devper.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import com.facebook.android.crypto.keychain.AndroidConceal
import com.facebook.android.crypto.keychain.SharedPrefsBackedKeyChain
import com.facebook.crypto.Crypto
import com.facebook.crypto.CryptoConfig
import com.facebook.crypto.Entity
import com.facebook.crypto.exception.CryptoInitializationException
import com.facebook.crypto.exception.KeyChainException
import com.facebook.crypto.keychain.KeyChain
import com.facebook.soloader.SoLoader
import java.io.IOException
import java.security.MessageDigest
import java.util.*

class SecurePreferences private constructor(
    context: Context,
    keyChain: KeyChain,
    private val entity: Entity,
    sharedPrefFilename: String
) : SharedPreferences {

    private val sharedPreferences: SharedPreferences
    private val crypto: Crypto

    class Builder(private val context: Context) {
        private var password: String? = null
        private lateinit var sharedPrefFilename: String

        fun password(password: String): Builder {
            this.password = password
            return this
        }

        fun filename(sharedPrefFilename: String): Builder {
            this.sharedPrefFilename = sharedPrefFilename
            return this
        }

        fun build(): SharedPreferences {
            if (!isInit) {
                Log.w(TAG, "You need call 'SecurePreferences.init()' in onCreate() from your application class.")
            }
            val keyChain = SharedPrefsBackedKeyChain(context, CryptoConfig.KEY_256)
            val entity = Entity.create(if (TextUtils.isEmpty(password)) context.packageName else password)
            return SecurePreferences(context, keyChain, entity, sharedPrefFilename)
        }
    }

    init {
        this.sharedPreferences = getSharedPreferenceFile(context, sharedPrefFilename)
        this.crypto = AndroidConceal.get().createCrypto256Bits(keyChain)
    }

    private fun encrypt(plainText: String?): String? {
        if (TextUtils.isEmpty(plainText)) {
            return plainText
        }

        var cipherText: ByteArray? = null

        if (!crypto.isAvailable) {
            log(Log.WARN, "encrypt: crypto not available")
            return null
        }

        try {
            cipherText = crypto.encrypt(plainText?.toByteArray(), entity)
        } catch (e: KeyChainException) {
            log(Log.ERROR, "encrypt: $e")
        } catch (e: CryptoInitializationException) {
            log(Log.ERROR, "encrypt: $e")
        } catch (e: IOException) {
            log(Log.ERROR, "encrypt: $e")
        }

        return if (cipherText != null) Base64.encodeToString(cipherText, Base64.DEFAULT) else null
    }

    private fun decrypt(encryptedText: String): String? {
        if (TextUtils.isEmpty(encryptedText)) {
            return encryptedText
        }

        var plainText: ByteArray? = null

        if (!crypto.isAvailable) {
            log(Log.WARN, "decrypt: crypto not available")
            return null
        }

        try {
            plainText = crypto.decrypt(Base64.decode(encryptedText, Base64.DEFAULT), entity)
        } catch (e: KeyChainException) {
            log(Log.ERROR, "decrypt: $e")
        } catch (e: CryptoInitializationException) {
            log(Log.ERROR, "decrypt: $e")
        } catch (e: IOException) {
            log(Log.ERROR, "decrypt: $e")
        }

        return if (plainText != null)
            String(plainText)
        else
            null
    }

    override fun getAll(): Map<String, *> {
        val encryptedMap = sharedPreferences.all
        val decryptedMap = HashMap<String, String>(encryptedMap.size)
        for ((key, cipherText) in encryptedMap) {
            try {
                if (cipherText != null) {
                    decryptedMap[key] = decrypt(cipherText.toString())!!
                }
            } catch (e: Exception) {
                log(Log.ERROR, "error getAll: $e")
                decryptedMap[key] = cipherText.toString()
            }
        }
        return decryptedMap
    }

    override fun getString(key: String, defaultValue: String?): String? {
        val encryptedValue = sharedPreferences.getString(hashKey(key), null)
        return encryptedValue?.let { decrypt(it) } ?: defaultValue
    }

    override fun getStringSet(key: String, defaultValues: Set<String>?): Set<String>? {
        val encryptedSet = sharedPreferences.getStringSet(hashKey(key), null) ?: return defaultValues
        val decryptedSet = HashSet<String>(encryptedSet.size)
        for (encryptedValue in encryptedSet) {
            decryptedSet.add(decrypt(encryptedValue)!!)
        }
        return decryptedSet
    }

    override fun getInt(key: String, defaultValue: Int): Int {
        val encryptedValue = sharedPreferences.getString(hashKey(key), null) ?: return defaultValue
        try {
            return Integer.parseInt(decrypt(encryptedValue)!!)
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }
    }

    override fun getLong(key: String, defaultValue: Long): Long {
        val encryptedValue = sharedPreferences.getString(hashKey(key), null) ?: return defaultValue
        try {
            return java.lang.Long.parseLong(decrypt(encryptedValue)!!)
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }
    }

    override fun getFloat(key: String, defaultValue: Float): Float {
        val encryptedValue = sharedPreferences.getString(hashKey(key), null) ?: return defaultValue
        try {
            return java.lang.Float.parseFloat(decrypt(encryptedValue)!!)
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }
    }

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        val encryptedValue = sharedPreferences.getString(hashKey(key), null) ?: return defaultValue
        try {
            return java.lang.Boolean.parseBoolean(decrypt(encryptedValue))
        } catch (e: NumberFormatException) {
            throw ClassCastException(e.message)
        }
    }

    override fun contains(key: String): Boolean {
        return sharedPreferences.contains(hashKey(key))
    }

    override fun edit(): Editor {
        return Editor()
    }

    inner class Editor @SuppressLint("CommitPrefEdits")

    constructor() : SharedPreferences.Editor {

        private val mEditor: SharedPreferences.Editor = sharedPreferences.edit()

        override fun putString(key: String, value: String?): SharedPreferences.Editor {
            mEditor.putString(hashKey(key), encrypt(value))
            return this
        }

        override fun putStringSet(key: String, values: Set<String>): SharedPreferences.Editor {
            val encryptedValues = HashSet<String>(values.size)
            for (value in values) {
                encryptedValues.add(encrypt(value)!!)
            }
            mEditor.putStringSet(hashKey(key), encryptedValues)
            return this
        }

        override fun putInt(key: String, value: Int): SharedPreferences.Editor {
            mEditor.putString(hashKey(key), encrypt(value.toString()))
            return this
        }

        override fun putLong(key: String, value: Long): SharedPreferences.Editor {
            mEditor.putString(hashKey(key), encrypt(value.toString()))
            return this
        }

        override fun putFloat(key: String, value: Float): SharedPreferences.Editor {
            mEditor.putString(hashKey(key), encrypt(value.toString()))
            return this
        }

        override fun putBoolean(key: String, value: Boolean): SharedPreferences.Editor {
            mEditor.putString(hashKey(key), encrypt(java.lang.Boolean.toString(value)))
            return this
        }

        override fun remove(key: String): SharedPreferences.Editor {
            mEditor.remove(hashKey(key))
            return this
        }

        override fun clear(): SharedPreferences.Editor {
            mEditor.clear()
            return this
        }

        override fun commit(): Boolean {
            return mEditor.commit()
        }

        override fun apply() {
            mEditor.apply()
        }
    }

    override fun registerOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    private fun getSharedPreferenceFile(context: Context, prefFilename: String): SharedPreferences {
        return if (TextUtils.isEmpty(prefFilename)) {
            PreferenceManager.getDefaultSharedPreferences(context)
        } else {
            context.getSharedPreferences(prefFilename, Context.MODE_PRIVATE)
        }
    }

    companion object {

        private val TAG = SecurePreferences::class.java.name
        private var isInit = false
        var isDebug = false

        fun init(pContext: Context) {
            SoLoader.init(pContext, false)
            isInit = true
        }

        private fun hashKey(key: String): String {
            val hexChars = "0123456789ABCDEF"
            val bytes = MessageDigest.getInstance("MD5").digest(key.toByteArray())
            val result = StringBuilder(bytes.size * 2)
            bytes.forEach {
                val i = it.toInt()
                result.append(hexChars[i shr 4 and 0x0f])
                result.append(hexChars[i and 0x0f])
            }
            return result.toString()
        }

        private fun log(type: Int, str: String) {
            if (isDebug) {
                when (type) {
                    Log.WARN -> Log.w(TAG, str)
                    Log.ERROR -> Log.e(TAG, str)
                    Log.DEBUG -> Log.d(TAG, str)
                }
            }
        }
    }

}
