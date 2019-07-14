package com.da62.presenter.write.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.da62.R
import com.da62.databinding.FragmentPlantRegistInfoBinding
import com.da62.presenter.base.BaseFragment
import com.da62.presenter.write.plant.PlantRegistViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlantInfoFragment : BaseFragment<FragmentPlantRegistInfoBinding>() {

    private val viewModel by sharedViewModel<PlantRegistViewModel>()

    override val resourceId = R.layout.fragment_plant_regist_info

    companion object {
        fun createInstance() =
            PlantInfoFragment().apply {

            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.viewModel = viewModel
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.plantLevel.value?.let { binding.levelGroup.check(it) }

        binding.levelGroup.setOnCheckedChangeListener { group, checkedId ->
            viewModel.setPlantLevel(checkedId)
        }
    }
}