package com.da62.presenter.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.da62.R
import com.da62.databinding.ActivityDetailBinding
import com.da62.presenter.base.BaseActivity
import com.da62.presenter.gallery.GalleryActivity
import com.da62.util.EXTRA_PLANT_ID
import com.da62.util.EXTRA_PLANT_THUMB_NAIL
import com.da62.util.dp2px
import com.da62.util.waterDialog
import kotlinx.android.synthetic.main.fragment_plant_regist_water_setting.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : BaseActivity() {

    private val viewModel by viewModel<DetailViewModel>()
    private lateinit var binding: ActivityDetailBinding

    private val detailId by lazy {
        intent.getIntExtra(EXTRA_PLANT_ID, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.detailHorizontalRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = DetailInfoAdapter(this@DetailActivity)
            addItemDecoration(
                DetailItemDecoration(
                    dp2px(this@DetailActivity, 24f),
                    dp2px(this@DetailActivity, 16f)
                )
            )
        }

        binding.detailGalleryRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@DetailActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        val infoSnapHelper = LinearSnapHelper()
        infoSnapHelper.attachToRecyclerView(binding.detailHorizontalRecyclerView)

        viewModel.clickToBack.observe(this, Observer {
            supportFinishAfterTransition()
        })

        viewModel.clickToGallery.observe(this, Observer {
            startActivityForResult(intentFor<GalleryActivity>(EXTRA_PLANT_ID to it), 1212)
        })

        viewModel.clickToWater.observe(this, Observer {
            waterDialog("${it}에게 물을 주시겠습니까?")
        })

        viewModel.errorMessage.observe(this, Observer {
            toast("연결에 실패했습니다.")
        })

        viewModel.plant.observe(this, Observer {
            (binding.detailHorizontalRecyclerView.adapter as DetailInfoAdapter).addPlant(it)
            binding.detailHorizontalRecyclerView.adapter?.notifyDataSetChanged()
        })

        viewModel.clickToTrash.observe(this, Observer {
            alert {
                title = "삭제"
                message = "정말 삭제하시겠습니까?"
                negativeButton("아니요") {}
                positiveButton("예") {
                    viewModel.deletePlant()
                }
            }.show()
        })

        viewModel.deleteSuccess.observe(this, Observer {
            finish()
        })

        val thumbNaiExtra = intent.getStringExtra(EXTRA_PLANT_THUMB_NAIL)
        viewModel.configureThumbnail(thumbNaiExtra)

        viewModel.loadDetail(detailId)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1212) {
            if (resultCode == Activity.RESULT_OK) {
               // viewModel.loadData()
            }
        }
    }

    // transition complete
    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()

    }
}