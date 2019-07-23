package com.da62.presenter.detail

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.da62.model.Plant
import com.da62.model.PlantInfo

@BindingAdapter("addPlantsInfo")
fun addPlantsInfo(recyclerView: RecyclerView, plantList: PlantInfo?) {
    plantList?.let {
        (recyclerView.adapter as DetailInfoAdapter).addItem(it)
    }
}