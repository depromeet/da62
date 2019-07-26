package com.da62.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.text.Editable
import android.util.TypedValue
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.text.SimpleDateFormat
import java.util.*

infix fun CompositeDisposable.add(d: Disposable) = this.add(d)

fun dp2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

fun String.toEditable() = Editable.Factory.getInstance().newEditable(this)

fun toDp(context: Context, size: Float) = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, size, context.resources.displayMetrics
).toInt()

fun Uri.toImagePath(context: Context): String? {
    var res: String? = null
    val cursor =
        context.contentResolver.query(this, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
    cursor?.let {
        if (it.moveToFirst()) {
            val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            res = it.getString(columnIndex)
        }
    }
    cursor?.close()
    return res
}

@SuppressLint("SimpleDateFormat")
fun Date?.toDateString(): String? {
    return this?.let {
        val format = SimpleDateFormat("yyyy-MM-dd")
        format.format(it)
    }
}

fun Date.toUploadDate(): String {
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS")
    return format.format(this)
}