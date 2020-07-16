package com.devper.template.core.platform.helper

import android.content.Context
import android.net.Uri
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL

fun fromURI(context: Context, uri: Uri): InputStreamWithSource? {
    return when (uri.scheme) {
        "content" -> fromContent(context, uri)
        "http", "https" -> return fromOKHttp(uri)
        "file" -> getDefaultInputStreamForUri(uri)
        else -> {
            getDefaultInputStreamForUri(uri)
        }
    }
}

private fun fromOKHttp(uri: Uri): InputStreamWithSource? {
    val client = OkHttpClient()
    val url = URL(uri.toString())
    val requestBuilder = Request.Builder().url(url)
    val request = requestBuilder.build()
    val response = client.newCall(request).execute()
    val body = response.body
    body?.let {
        return InputStreamWithSource(uri.toString(), it.byteStream())
    }
    return null
}

private fun fromContent(ctx: Context, uri: Uri) = InputStreamWithSource(uri.toString(), ctx.contentResolver.openInputStream(uri))

private fun getDefaultInputStreamForUri(uri: Uri) = InputStreamWithSource(uri.toString(), BufferedInputStream(URL(uri.toString()).openStream(), 4096))

data class InputStreamWithSource(val source: String, val inputStream: InputStream?)