package com.da62.presenter.main

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

        snapHelper.attachToRecyclerView(binding.mainRecyclerView)

        viewModel.clickToAdd.observe(this, Observer {
            startActivity(intentFor<PlantRegistActivity>())
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
}

interface MainEventListener {

    fun onItemClick(view: View, position: Int, plant: Plant)
}