package com.da62.presenter.write.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.da62.R
import com.da62.databinding.FragmentPlantRegistInfoBinding
import com.da62.presenter.base.BaseFragment
import com.da62.presenter.write.plant.PlantRegistViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlantInfoFragment : BaseFragment<FragmentPlantRegistInfoBinding>() {

    private val mViewModel by sharedViewModel<PlantRegistViewModel>()

    override val mResourceId = R.layout.fragment_plant_regist_info

    companion object {
        fun createInstance() =
            PlantInfoFragment().apply {

            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            mBinding.viewModel = mViewModel
        }

}