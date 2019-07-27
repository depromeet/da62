package com.da62.presenter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.da62.databinding.ItemDetailImageBinding

class DetailImageAdapter(private val lifecycleOwner: LifecycleOwner) :
    ListAdapter<String, DetailImageViewHolder>(imageDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val binding =
            ItemDetailImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return DetailImageViewHolder(binding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class DetailImageViewHolder(
    private val binding: ItemDetailImageBinding,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl: String?) {
        binding.image = imageUrl
        binding.lifecycleOwner = lifecycleOwner
        binding.executePendingBindings()
    }
}

val imageDiffUtil = object : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean = oldItem == newItem
}