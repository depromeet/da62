package com.da62.presenter.gallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.da62.R
import com.da62.databinding.ActivityGalleryBinding
import com.da62.presenter.base.BaseActivity
import com.da62.util.EXTRA_PLANT_ID
import com.da62.util.toImagePath
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class GalleryActivity : BaseActivity() {

    companion object {
        private const val EXTRA_ALBUM = 121
    }

    private lateinit var binding: ActivityGalleryBinding
    private val viewModel by viewModel<GalleryViewModel>()

    private val plantId by lazy {
        intent.getIntExtra(EXTRA_PLANT_ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_gallery)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.openAlbum.observe(this, Observer {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(Intent.createChooser(intent, "album"), EXTRA_ALBUM)
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

}