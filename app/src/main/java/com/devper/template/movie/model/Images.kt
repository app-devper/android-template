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
            if (baseUrl != null && baseUrl!!.isNotEmpty()) {
                if (posterSizes != null) {
                    if (posterSizes!!.size > 4) {
                        // usually equal to 'w500'
                        return baseUrl + posterSizes!![4]
                    } else {
                        // back-off to hard-coded value
                        return baseUrl + "w500"
                    }
                }
            }
            return ""
        }
}
