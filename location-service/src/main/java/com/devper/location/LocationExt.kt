package com.devper.location

import android.location.Location

interface LocationCallBack {
    fun onLocationResult(location: Location?)
}

