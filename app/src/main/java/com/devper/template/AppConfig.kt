package com.devper.template

object AppConfig {

    const val SPLASH_DELAY: Long = 3000 // 3 seconds
    private const val URL = "https://common-api-app.herokuapp.com/"

    val url: String
        get() {
            return URL
        }

}


