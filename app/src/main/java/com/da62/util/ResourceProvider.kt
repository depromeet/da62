package com.da62.util

import android.content.ContentResolver
import android.content.Context

interface ResourceProvider {

    fun getString(resId: Int): String

    fun getContentResolver(): ContentResolver

}

class ResourceProviderImpl(private val context: Context) : ResourceProvider {

    override fun getString(resId: Int): String {
        return context.getString(resId)
    }

    override fun getContentResolver(): ContentResolver {
        return context.contentResolver
    }
}
