package com.da62.presenter.write.plant.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.da62.R
import com.da62.databinding.DialogWaterTimePickerBinding

class PlantWaterTimePickerDialog(private val callback: (Pair<Int, Int>) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogWaterTimePickerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        DataBindingUtil.inflate<DialogWaterTimePickerBinding>(
            inflater, R.layout.dialog_water_time_picker, container, false
        ).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.confirm.setOnClickListener {
            callback.invoke(Pair(binding.timePicker.currentHour, binding.timePicker.currentMinute))
            dismiss()
        }
        binding.cancel.setOnClickListener {
            dismiss()
        }
    }
}