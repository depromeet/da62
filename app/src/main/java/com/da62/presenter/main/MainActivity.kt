package com.da62.presenter.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.da62.R
import com.da62.databinding.ActivityMainBinding
import com.da62.model.ListType
import com.da62.model.Plant
import com.da62.presenter.base.BaseActivity
import com.da62.presenter.detail.DetailActivity
import com.da62.presenter.write.plant.PlantRegistActivity
import com.da62.util.EXTRA_PLANT_ID
import com.da62.util.EXTRA_PLANT_THUMB_NAIL
import com.da62.util.dp2px
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity(), MainEventListener {

    private val viewModel by viewModel<MainViewModel>()
    private lateinit var binding: ActivityMainBinding

    private val snapHelper by lazy { LinearSnapHelper() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.mainRecyclerView.apply {
            layoutManager =
                GridLayoutManager(this@MainActivity, 1, LinearLayoutManager.HORIZONTAL, false)
            adapter = MainAdapter(this@MainActivity, this@MainActivity, ListType.LIST)
            addItemDecoration(
                MainItemDecoration(
                    dp2px(this@MainActivity, 24f),
                    dp2px(this@MainActivity, 16f),
                    ListType.LIST
                )
            )
        }
        binding.mainIndicator.attachToRecyclerView(binding.mainRecyclerView)

        snapHelper.attachToRecyclerView(binding.mainRecyclerView)

        viewModel.clickToAdd.observe(this, Observer {
            startActivityForResult(intentFor<PlantRegistActivity>(), 0x100)
        })

        viewModel.errorMessage.observe(this, Observer {
            toast("연결에 실패했습니다.")
        })

        viewModel.refreshPosition.observe(this, Observer {
            binding.mainRecyclerView.adapter?.notifyItemChanged(it)
        })
    }

    override fun onItemClick(view: View, position: Int, plant: Plant) {
        val imageView = view.findViewById<View>(R.id.item_main_image)
        val pair = Pair(imageView, imageView.transitionName)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pair)
        startActivity(
            intentFor<DetailActivity>(
                EXTRA_PLANT_ID to plant.id,
                EXTRA_PLANT_THUMB_NAIL to plant.card
            ),
            optionsCompat.toBundle()
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0x100) {
            if (resultCode == Activity.RESULT_OK) {
                viewModel.loadData()
            }
        }
    }

    override fun clickToLove(id: Int, position: Int) {
        viewModel.clickToLove(id, position)
    }
}

interface MainEventListener {

    fun onItemClick(view: View, position: Int, plant: Plant)

    fun clickToLove(id: Int, position: Int)
}