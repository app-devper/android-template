package com.devper.template.core.picker

import android.net.Uri

interface PickerCallback {
    fun onSuccess(imagePath: Uri?)
    fun onCancel()
}