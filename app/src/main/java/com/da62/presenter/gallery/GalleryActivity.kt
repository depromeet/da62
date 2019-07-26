package com.da62.presenter.gallery

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.da62.R
import com.da62.databinding.ActivityGalleryBinding
import com.da62.presenter.base.BaseActivity
import com.da62.util.EXTRA_PLANT_ID
import com.da62.util.PermissionProvider
import com.da62.util.toImagePath
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class GalleryActivity : BaseActivity() {

    companion object {
        private const val EXTRA_ALBUM = 121
        private const val REQUEST_GALLERY = 122
    }

    private lateinit var binding: ActivityGalleryBinding
    private val viewModel by viewModel<GalleryViewModel>()

    private val permissionProvider by inject<PermissionProvider>()

    private val plantId by lazy {
        intent.getIntExtra(EXTRA_PLANT_ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.openAlbum.observe(this, Observer {
            checkPermission()
        })

        viewModel.clickToBack.observe(this, Observer {
            finish()
        })

        viewModel.showToast.observe(this, Observer {
            toast(it)
        })

        viewModel.uploadComplete.observe(this, Observer {
            finish()
        })

        viewModel.configureGallery(plantId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == EXTRA_ALBUM) {
                val uri = data?.data
                uri?.toImagePath(this)?.let {
                    viewModel.setGalleryImage(it)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_GALLERY) {
            if (permissions.size == 1 &&
                permissions[0] == Manifest.permission.READ_EXTERNAL_STORAGE &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                permissionDeniedDialog()
            }
        }
    }

    private fun checkPermission() {
        permissionProvider.hasGalleryPermissionDenied {
            if (it) {
                permissionProvider.requestGalleryPermission(this, REQUEST_GALLERY)
            } else {
                openGallery()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(Intent.createChooser(intent, "album"), EXTRA_ALBUM)
    }

    private fun permissionDeniedDialog() {
        alert {
            title = "권한 안내"
            message = "권한이 거부되어있습니다. [설정] 에서 권한을 허용해야 합니다."
            positiveButton("확인") {}
        }.show()
    }

}