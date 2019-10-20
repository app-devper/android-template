package com.devper.template.domain.model.movie

data class Images(
    val baseUrl: String?,
    val secureBaseUrl: String?,
    val backdropSizes: List<String>?,
    val logoSizes: List<String>?,
    val posterSizes: List<String>?,
    val profileSizes: List<String>?,
    val stillSizes: List<String>?
) {
    val baseUrlFull: String
        get() {
            if (secureBaseUrl != null) {
                posterSizes?.let {
                    return if (it.size > 4) {
                        // usually equal to 'w500'
                        secureBaseUrl + it[4]
                    } else {
                        // back-off to hard-coded value
                        secureBaseUrl + "w500"
                    }
                }
            }
            return ""
        }
}
