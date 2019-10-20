package com.devper.template.data.remote.movie

import com.google.gson.annotations.SerializedName

data class ImagesData(
    @SerializedName("base_url")
    val baseUrl: String?,
    @SerializedName("secure_base_url")
    val secureBaseUrl: String?,
    @SerializedName("backdrop_sizes")
    val backdropSizes: List<String>?,
    @SerializedName("logo_sizes")
    val logoSizes: List<String>?,
    @SerializedName("poster_sizes")
    val posterSizes: List<String>?,
    @SerializedName("profile_sizes")
    val profileSizes: List<String>?,
    @SerializedName("still_sizes")
    val stillSizes: List<String>?
){
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

