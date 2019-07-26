package com.da62.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

interface PermissionProvider {

    fun requestGalleryPermission(activity: Activity, requestCode: Int)

    fun hasGalleryPermissionDenied(isDenied: (Boolean) -> Unit)
}

class PermissionProviderImpl(private val context: Context) : PermissionProvider {

    override fun requestGalleryPermission(activity: Activity, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ),
            requestCode
        )
    }

    override fun hasGalleryPermissionDenied(isDenied: (Boolean) -> Unit) {
        val permission = ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) != PackageManager.PERMISSION_GRANTED

        isDenied(permission)
    }
}