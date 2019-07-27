package com.da62.util

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.da62.R
import com.da62.model.ListType
import com.da62.model.Plant
import com.da62.presenter.main.MainAdapter
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

@BindingAdapter("mainItems")
fun setAddItems(recyclerView: RecyclerView, items: List<Plant>?) {
    items?.let { (recyclerView.adapter as MainAdapter).submitList(it) }
}

@BindingAdapter("mainListType")
fun setListType(recyclerView: RecyclerView, type: ListType?) {
    type?.let {
        /*   val manager = when (it) {
               ListType.LIST -> GridLayoutManager(
                   recyclerView.context,
                   1,
                   LinearLayoutManager.HORIZONTAL,
                   false
               )

               else -> GridLayoutManager(recyclerView.context, 2)
           }
   */
        recyclerView.run {
            // layoutManager = manager
            val manager = layoutManager as GridLayoutManager
            if (manager.spanCount == 1) {
                manager.spanCount = 3
            } else {
                manager.spanCount = 1
            }
            (adapter as MainAdapter).setViewType(it)
            adapter?.notifyItemRangeChanged(0, adapter?.itemCount ?: 0)
        }
    }
}

@BindingAdapter("goneUnless")
fun goneUnless(view: View, visible: Boolean?) {
    visible?.let {
        if (it) {
            view.visibility = VISIBLE
        } else {
            view.visibility = GONE
        }
    } ?: run {
        view.visibility = GONE
    }
}


@BindingAdapter("growThumbNail")
fun growThumbNail(imageView: ImageView, grow: String?) {
    grow?.let {
        val src = when (it) {
            "TREE" -> R.drawable.ic_illust_2
            "DRUPE" -> R.drawable.ic_illust_5
            "GRASS" -> R.drawable.ic_illust_1
            "LEAF" -> R.drawable.ic_illust_3
            else -> R.drawable.ic_illust_4
        }

        Glide.with(imageView)
            .load(src)
            .into(imageView)
    }
}

@BindingAdapter("imageUrl")
fun imageUrl(imageView: ImageView, path: String?) {
    path?.let {
        Glide.with(imageView)
            .load(it)
            .into(imageView)
    } ?: run {
        Glide.with(imageView)
            .load(R.drawable.ic_camera_placeholder)
            .into(imageView)
    }
}

@BindingAdapter(value = ["growCheckedType", "growType"])
fun growType(checkBox: CheckBox, checkedType: String, growType: String?) {

    val isChecked = growType?.let {
        checkedType == it
    } ?: false
    checkBox.isChecked = isChecked

}

@BindingAdapter("waterDate")
fun setWaterDate(view: TextView, date: String?) {
    view.text = " 부터"
    date?.let {
        view.text = "${it.replace("-", ":")} ${view.text}"
    }
}

@BindingAdapter("waterTime")
fun setWaterTime(view: TextView, time: String?) {
    time?.let {
        val hourMin = it.split(":")
        var hour = hourMin[0]
        var min = hourMin[1]
        var meridiem = "AM"
        if (hour.toInt() >= 12) {
            meridiem = "PM"
            hour = "${hour.toInt() - 12}"
        }
        if (hour.length == 1) {
            hour = "0$hour"
        }
        if (min.length == 1) {
            min = "0$min"
        }
        view.text = "$hour : $min $meridiem"
    }
}

@BindingAdapter("roundImage")
fun roundImage(imageView: ImageView, url: String?) {
    url?.let {
        val option = MultiTransformation(
            CenterCrop(),
            RoundedCornersTransformation(
                dp2px(imageView.context.applicationContext, 10f),
                0,
                RoundedCornersTransformation.CornerType.ALL
            )
        )

        Glide.with(imageView.context)
            .load(it)
            .apply(RequestOptions.bitmapTransform(option))
            .into(imageView)
    }
}