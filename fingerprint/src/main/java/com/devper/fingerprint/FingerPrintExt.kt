package com.devper.fingerprint

/**
 * A callback that allows a class to be updated when fingerprint authentication is complete.
 */
interface Callback {
    /**
     * Callback method used for a successful fingerprint authentication.
     */
    fun onAuthenticated()

    /**
     * Callback method used if there is any error authenticating the fingerprint.
     */
    fun onError()
}