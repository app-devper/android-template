package com.devper.imagepicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import java.io.File

const val REQUEST_IMAGE = 100

const val INTENT_IMAGE_PICKER_OPTION = "image_picker_option"
const val INTENT_ASPECT_RATIO_X = "aspect_ratio_x"
const val INTENT_ASPECT_RATIO_Y = "aspect_ratio_Y"
const val INTENT_LOCK_ASPECT_RATIO = "lock_aspect_ratio"
const val INTENT_IMAGE_COMPRESSION_QUALITY = "compression_quality"
const val INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT = "set_bitmap_max_width_height"
const val INTENT_BITMAP_MAX_WIDTH = "max_width"
const val INTENT_BITMAP_MAX_HEIGHT = "max_height"
const val REQUEST_IMAGE_CAPTURE = 0
const val REQUEST_GALLERY_IMAGE = 1

interface PickerOptionListener {
    fun onTakeCameraSelected()

    fun onChooseGallerySelected()
}

fun showImagePickerOptions(activity: Activity) {
    ImagePickerActivity.showImagePickerOptions(activity, object : PickerOptionListener {
        override fun onTakeCameraSelected() {
            launchCameraIntent(activity)
        }

        override fun onChooseGallerySelected() {
            launchGalleryIntent(activity)
        }
    })
}

fun clearCache(context: Context) {
    val path = File(context.externalCacheDir, "camera")
    if (path.exists() && path.isDirectory) {
        for (child in path.listFiles()) {
            child.delete()
        }
    }
}

private fun launchCameraIntent(context: Activity) {
    val intent = Intent(context, ImagePickerActivity::class.java)
    intent.putExtra(INTENT_IMAGE_PICKER_OPTION, REQUEST_IMAGE_CAPTURE)

    // setting aspect ratio
    intent.putExtra(INTENT_LOCK_ASPECT_RATIO, true)
    intent.putExtra(INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
    intent.putExtra(INTENT_ASPECT_RATIO_Y, 1)

    // setting maximum bitmap width and height
    intent.putExtra(INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true)
    intent.putExtra(INTENT_BITMAP_MAX_WIDTH, 1000)
    intent.putExtra(INTENT_BITMAP_MAX_HEIGHT, 1000)

    context.startActivityForResult(intent, REQUEST_IMAGE)
}

private fun launchGalleryIntent(context: Activity) {
    val intent = Intent(context, ImagePickerActivity::class.java)
    intent.putExtra(INTENT_IMAGE_PICKER_OPTION, REQUEST_GALLERY_IMAGE)

    // setting aspect ratio
    intent.putExtra(INTENT_LOCK_ASPECT_RATIO, true)
    intent.putExtra(INTENT_ASPECT_RATIO_X, 1) // 16x9, 1x1, 3:4, 3:2
    intent.putExtra(INTENT_ASPECT_RATIO_Y, 1)
    context.startActivityForResult(intent, REQUEST_IMAGE)
}