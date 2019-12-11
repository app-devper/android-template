package com.devper.template.core.picker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider.getUriForFile
import java.io.File

class ImagePickerConfig(private val activity: Activity, val callback: PickerCallback) {

    fun takeCameraImage() {
        fileName = System.currentTimeMillis().toString() + ".jpg"
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(fileName))
        if (takePictureIntent.resolveActivity(activity.packageManager) != null) {
            activity.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    fun chooseImageFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        activity.startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE)
    }

    fun openFileManager() {
        val mimeTypes = arrayOf("application/pdf", "image/jpeg", "image/gif", "image/png", "image/tiff")
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        activity.startActivityForResult(intent, REQUEST_FILE_MANAGER)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> if (resultCode == Activity.RESULT_OK) {
                setResultOk(getCacheImagePath(fileName))
            } else {
                setResultCancelled()
            }
            REQUEST_GALLERY_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                val imageUri = data?.data
                setResultOk(imageUri!!)
            } else {
                setResultCancelled()
            }
            REQUEST_FILE_MANAGER -> if (resultCode == Activity.RESULT_OK) {
                val imageUri = data?.data
                setResultOk(imageUri!!)
            } else {
                setResultCancelled()
            }
            else -> setResultCancelled()
        }
    }

    private fun setResultOk(imagePath: Uri?) {
        callback.onSuccess(imagePath)
    }

    private fun setResultCancelled() {
        callback.onCancel()
    }

    private fun getCacheImagePath(fileName: String): Uri {
        val path = File(activity.externalCacheDir, "camera")
        if (!path.exists()) path.mkdirs()
        val image = File(path, fileName)
        return getUriForFile(activity, "${activity.packageName}.provider", image)
    }

    companion object {
        private lateinit var fileName: String
        private const val REQUEST_IMAGE_CAPTURE = 0
        private const val REQUEST_GALLERY_IMAGE = 1
        private const val REQUEST_FILE_MANAGER = 2
    }
}
