package com.devper.template.movie.model

import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("base_url")
    var baseUrl: String? = null,
    @SerializedName("secure_base_url")
    var secureBaseUrl: String? = null,
    @SerializedName("backdrop_sizes")
    var backdropSizes: List<String>? = null,
    @SerializedName("logo_sizes")
    var logoSizes: List<String>? = null,
    @SerializedName("poster_sizes")
    var posterSizes: List<String>? = null,
    @SerializedName("profile_sizes")
    var profileSizes: List<String>? = null,
    @SerializedName("still_sizes")
    var stillSizes: List<String>? = null
) {
    val baseUrlFull: String
        get() {
            if (baseUrl != null) {
                posterSizes?.let {
                    return if (it.size > 4) {
                        // usually equal to 'w500'
                        baseUrl + it[4]
                    } else {
                        // back-off to hard-coded value
                        baseUrl + "w500"
                    }
                }
            }
            return ""
        }
}
