package com.da62.presenter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.da62.databinding.ItemDetailInfoBBinding
import com.da62.databinding.ItemDetailInfoBinding
import com.da62.databinding.ItemDetailInfoCBinding
import com.da62.databinding.ItemDetailInfoDBinding
import com.da62.model.Plant
import com.da62.model.PlantInfo

class DetailInfoAdapter(
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var plant = Plant()

    private lateinit var plantInfo: PlantInfo

    private companion object {
        private const val A = 0
        private const val B = 1
        private const val C = 2
        private const val D = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            B -> {
                val binding = ItemDetailInfoBBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DetailInfoBViewHolder(binding, lifecycleOwner)
            }
            C -> {
                val binding = ItemDetailInfoCBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DetailInfoCViewHolder(binding, lifecycleOwner)
            }
            D -> {
                val binding = ItemDetailInfoDBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DetailInfoDViewHolder(binding, lifecycleOwner)
            }
            else -> {
                val binding = ItemDetailInfoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DetailInfoAViewHolder(binding, lifecycleOwner)
            }
        }
    }

    fun addItem(plantInfo: PlantInfo) {
        this.plantInfo = plantInfo
    }

    fun addPlant(plant: Plant) {
        this.plant = plant
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> A
            1 -> B
            2 -> C
            3 -> D
            else -> 0
        }
    }


    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (::plantInfo.isInitialized) {
            when (position) {
                0 -> (holder as DetailInfoAViewHolder).bind(plantInfo, plant)
                1 -> (holder as DetailInfoBViewHolder).bind(plantInfo)
                2 -> (holder as DetailInfoCViewHolder).bind(plantInfo)
                3 -> (holder as DetailInfoDViewHolder).bind(plantInfo)
            }
        }
    }
}

class DetailInfoAViewHolder(
    private val binding: ItemDetailInfoBinding,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plantInfo: PlantInfo, plant: Plant) {
        binding.plantInfo = plantInfo
        binding.plant = plant
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}

class DetailInfoBViewHolder(
    private val binding: ItemDetailInfoBBinding,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plantInfo: PlantInfo) {
        binding.plantInfo = plantInfo
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}

class DetailInfoCViewHolder(
    private val binding: ItemDetailInfoCBinding,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plantInfo: PlantInfo) {
        binding.plantInfo = plantInfo
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}

class DetailInfoDViewHolder(
    private val binding: ItemDetailInfoDBinding,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(plantInfo: PlantInfo) {
        binding.plantInfo = plantInfo
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}