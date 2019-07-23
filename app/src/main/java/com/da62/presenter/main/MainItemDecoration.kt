package com.da62.presenter.main

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.da62.model.ListType

class MainItemDecoration(
    private val start: Int,
    private val default: Int,
    private val viewType: ListType = ListType.EMPTY_LIST
) : RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        if (viewType == ListType.LIST) {
            outRect.right = default
            val position = parent.getChildAdapterPosition(view)
            val totalCount =
                parent.adapter?.itemCount ?: throw IllegalArgumentException("Missing Adapter!")
            if (position == 0) {
                outRect.left = start
            }

            if (position == totalCount - 1) {
                outRect.right = start
            }
        }
    }
}