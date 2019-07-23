package com.da62.presenter.write.plant.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.da62.R
import com.da62.databinding.FragmentPlantRegistWaterSettingBinding
import com.da62.presenter.base.BaseFragment
import com.da62.presenter.write.plant.PlantRegistViewModel
import com.da62.presenter.write.plant.dialog.PlantWaterCalendarDialog
import com.da62.presenter.write.plant.dialog.PlantWaterTimePickerDialog
import org.jetbrains.anko.sdk27.coroutines.onCheckedChange
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PlantWaterSettingFragment : BaseFragment<FragmentPlantRegistWaterSettingBinding>() {

    private val viewModel by sharedViewModel<PlantRegistViewModel>()

    override val resourceId = R.layout.fragment_plant_regist_water_setting

    companion object {
        fun createInstance() =
            PlantWaterSettingFragment().apply {

            }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        super.onCreateView(inflater, container, savedInstanceState).apply {
            binding.viewModel = viewModel
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.date.setOnClickListener {
            activity?.let {
                PlantWaterCalendarDialog { date ->
                    viewModel.waterDate.value = "${date.first}.${date.second}.${date.third}"
                }.show(it.supportFragmentManager, null)
            }
        }
        binding.waterTime.setOnClickListener {
            activity?.let {
                PlantWaterTimePickerDialog { time ->
                    viewModel.waterTime.value = "${time.first}:${time.second}"
                }.show(it.supportFragmentManager, null)
            }
        }
        binding.alarmSwitch.onCheckedChange { _, isChecked ->
            viewModel.isWaterAlarmChecked.value = isChecked
        }
    }
}