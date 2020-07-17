package com.devper.template.data.session

import com.devper.template.data.remote.movie.ConfigurationData

data class AppSession(
    var configurationData: ConfigurationData? = null,
    var accessToken: String? = null
)