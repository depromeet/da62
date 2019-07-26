package com.da62.presenter.write.plant

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.da62.R
import com.da62.databinding.ActivityPlantRegistBinding
import com.da62.presenter.base.BaseActivity
import com.da62.presenter.write.plant.fragment.PlantInfoFragment
import com.da62.presenter.write.plant.fragment.PlantSpeciesFragment
import com.da62.presenter.write.plant.fragment.PlantWaterSettingFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlantRegistActivity : BaseActivity() {

    private val viewModel by viewModel<PlantRegistViewModel>()

    private lateinit var binding: ActivityPlantRegistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_plant_regist)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        init()
        nextObserve()
        errorObserve()
    }

    private fun init() {
        initViewPager(
            createViewPagerAdapter(
                listOf(
                    PlantSpeciesFragment.createInstance(),
                    PlantInfoFragment.createInstance(),
                    PlantWaterSettingFragment.createInstance()
                )
            )
        )
    }

    private fun initViewPager(pagerAdapter: PagerAdapter) = binding.viewPager.apply {
        adapter = pagerAdapter
        currentItem = 0
        beginFakeDrag()
    }

    private fun createViewPagerAdapter(pages: List<Fragment>) =
        PlantRegistViewPagerAdapter(supportFragmentManager)
            .apply {
                setPage(pages)
            }

    private fun nextObserve() = viewModel.next.observe(this, Observer {
        binding.viewPager.apply {
            if (isLastPage()) {
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                currentItem += 1
            }
        }
    })

    private fun isLastPage() = binding.viewPager.let {
        it.currentItem == it.childCount
    }

    private fun errorObserve() = viewModel.error.observe(this, Observer {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    })

    override fun onBackPressed() {
        binding.viewPager.apply {
            if (currentItem != 0) {
                currentItem--
            } else {
                super.onBackPressed()
            }
        }
    }
}

class PlantRegistViewPagerAdapter(private val mFragmentManager: FragmentManager) :
    FragmentPagerAdapter(mFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = mutableListOf<Fragment>()

    override fun getItem(position: Int) = pages[position]

    override fun getCount() = pages.size

    fun setPage(pages: List<Fragment>) {
        this.pages.addAll(pages)
    }
}