package com.da62.util

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import java.io.ByteArrayOutputStream
import java.io.File


class ImageUtil(private val provider: ResourceProvider) {

    fun bitmapTransform(file: File): ByteArray {

        val uri = Uri.fromFile(file)
        val bmp = MediaStore.Images.Media.getBitmap(provider.getContentResolver(), uri)
        val baos = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.JPEG, 25, baos)

        return baos.toByteArray()
    }
}