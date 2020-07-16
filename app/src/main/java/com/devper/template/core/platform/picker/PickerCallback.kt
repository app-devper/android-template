package com.devper.template.core.platform.picker

import android.net.Uri

interface PickerCallback {
    fun onSuccess(imagePath: Uri?)
    fun onCancel()
}