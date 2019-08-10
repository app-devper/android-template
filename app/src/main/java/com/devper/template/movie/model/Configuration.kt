package com.devper.template.movie.model

import com.google.gson.annotations.SerializedName

data class Configuration(
    @SerializedName("images")
    var images: Images? = null,
    @SerializedName("change_keys")
    var changeKeys: List<String>? = null
)
