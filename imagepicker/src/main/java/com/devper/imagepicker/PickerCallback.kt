package com.devper.imagepicker

import android.net.Uri

interface PickerCallback {
    fun onSuccess(imagePath: Uri?)
    fun onCancel()
}