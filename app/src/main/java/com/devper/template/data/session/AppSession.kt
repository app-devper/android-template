package com.devper.template.data.session

interface AppSessionProvider {
    var accessToken: String?
}

data class AppSession constructor(
    override var accessToken: String? = null
) : AppSessionProvider