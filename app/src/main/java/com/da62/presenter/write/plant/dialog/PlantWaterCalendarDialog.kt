package com.da62.presenter.write.plant.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.da62.R
import com.da62.databinding.DialogWaterCalendarBinding

class PlantWaterCalendarDialog(private val callback: (Triple<Int, Int, Int>) -> Unit) : DialogFragment() {

    private lateinit var binding: DialogWaterCalendarBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        DataBindingUtil.inflate<DialogWaterCalendarBinding>(
            inflater, R.layout.dialog_water_calendar, container, false
        ).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendar.setOnDateChangeListener { _, year, month, dayOfMonth ->
            callback.invoke(Triple(year, month, dayOfMonth))
            dismiss()
        }
    }
}