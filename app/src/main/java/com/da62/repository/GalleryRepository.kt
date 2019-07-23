package com.da62.repository

import com.da62.datasource.api.ApiService
import com.da62.datasource.local.PreferenceStorage
import com.da62.model.Plant
import com.da62.model.Response
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface GalleryRepository {

    fun uploadImage(
        image: MultipartBody.Part,
        params: Map<String, RequestBody>
    ): Single<Response<Plant>>
}

class GalleryRepositoryImpl(
    private val apiService: ApiService,
    private val preferenceStorage: PreferenceStorage
) : GalleryRepository {

    override fun uploadImage(
        image: MultipartBody.Part,
        params: Map<String, RequestBody>
    ): Single<Response<Plant>> {
        return apiService.uploadImage(
            accessToken = preferenceStorage.accessToken ?: "",
            image = image,
            params = params
        ).subscribeOn(Schedulers.io())
    }
}