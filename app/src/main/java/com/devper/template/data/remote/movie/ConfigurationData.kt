package com.devper.template.data.remote.movie

import com.google.gson.annotations.SerializedName

data class ConfigurationData(
    @SerializedName("images")
    val images: ImagesData,
    @SerializedName("change_keys")
    val changeKeys: List<String>
)
