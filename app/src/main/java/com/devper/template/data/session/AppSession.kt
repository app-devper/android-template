package com.devper.template.data.session

import com.devper.template.domain.provider.AppSessionProvider

data class AppSession constructor(
    override var accessToken: String? = null
) : AppSessionProvider