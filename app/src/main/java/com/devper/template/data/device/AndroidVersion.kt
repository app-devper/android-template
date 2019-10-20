package com.devper.template.data.device

import com.devper.template.BuildConfig
import com.devper.template.domain.model.version.Version

class AndroidVersion : Version {

    override fun version(): String = BuildConfig.VERSION_NAME
    
    override fun versionCode(): String = BuildConfig.VERSION_CODE.toString()
}