package com.devper.fingerprint

import android.util.Log
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executors

class BiometricController(var activity: FragmentActivity, callback: Callback) {

    private val executor = Executors.newSingleThreadExecutor()
    private var biometricPrompt: BiometricPrompt
    private var promptInfo: BiometricPrompt.PromptInfo? = null

    init {
        biometricPrompt = BiometricPrompt(activity, executor, object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Log.i(TAG, "onAuthenticationError: $errString")
                if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    // user clicked negative button
                } else {
                    callback.onError()
                }
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Log.i(TAG, "onAuthenticationSucceeded")
                callback.onAuthenticated()
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Log.i(TAG, "onAuthenticationFailed")
                callback.onError()
            }
        })
    }

    fun setPromptInfo(title: String, subTitle: String, description: String, negative: String) {
        promptInfo = BiometricPrompt.PromptInfo.Builder().run {
            setTitle(title)
            setSubtitle(subTitle)
            setDescription(description)
            setNegativeButtonText(negative)
            build()
        }
    }

    fun setPromptInfo(prompt: BiometricPrompt.PromptInfo) {
        promptInfo = prompt
    }

    fun authenticate() {
        if (promptInfo == null) {
            promptInfo = BiometricPrompt.PromptInfo.Builder().run {
                setTitle(activity.getString(R.string.fingerprint_recognized))
                setSubtitle(activity.getString(R.string.touch_sensor))
                setNegativeButtonText(activity.getString(R.string.cancel))
                build()
            }
        }

        biometricPrompt.authenticate(promptInfo!!)
    }

    fun cancel() {
        biometricPrompt.cancelAuthentication()
    }

    companion object {
        private val TAG: String = BiometricController::class.java.simpleName
    }
}