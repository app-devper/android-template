package com.devper.template.core.extension

import android.graphics.Bitmap
import java.io.ByteArrayOutputStream

fun Bitmap.toByteArray(quality: Int = 80): ByteArray {
    val stream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, quality, stream)
    return stream.toByteArray()
}