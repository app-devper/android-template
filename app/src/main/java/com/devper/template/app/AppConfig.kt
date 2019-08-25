package com.devper.template.app

object AppConfig {

    const val SPLASH_DELAY: Long = 3000 // 3 seconds
    private const val URL = "https://common-api-app.herokuapp.com/"
    const val MOVIE_URL = "https://api.themoviedb.org/"

    val url: String
        get() {
            return URL
        }

}


