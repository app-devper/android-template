package com.devper.template.core.platform.location

import android.location.Location

interface LocationCallBack {
    fun onLocationResult(location: Location?)
}

