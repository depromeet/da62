package com.da62.repository

import com.da62.datasource.api.ApiService
import com.da62.datasource.local.PreferenceStorage
import com.da62.model.Plant
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface DetailRepository {

    fun getDetail(id: Int): Single<Plant>

    fun postLove(id: Int): Single<Plant>

    fun delete(id: Int): Single<String>

    fun plantImageList(id: Int): Single<List<String>>
}

class DetailRepositoryImpl(
    private val apiService: ApiService,
    private val preferenceStorage: PreferenceStorage
) : DetailRepository {

    override fun getDetail(id: Int): Single<Plant> {
        return apiService.getDetail(
            accessToken = preferenceStorage.accessToken ?: "",
            id = id
        ).subscribeOn(Schedulers.io())
    }

    override fun postLove(id: Int): Single<Plant> {
        return apiService.postLove(
            accessToken = preferenceStorage.accessToken ?: "",
            id = id
        ).subscribeOn(Schedulers.io())
    }

    override fun delete(id: Int): Single<String> {
        return apiService.deletePlant(
            accessToken = preferenceStorage.accessToken ?: "",
            id = id,
            userId = preferenceStorage.userId
        ).subscribeOn(Schedulers.io())
    }

    override fun plantImageList(id: Int): Single<List<String>> {
        return apiService.getPlantImageList(
            accessToken = preferenceStorage.accessToken ?: "",
            plantId = id,
            userId = preferenceStorage.userId
        )
            .subscribeOn(Schedulers.io())
            .map {
                it.map { url ->
                    url.imageUrl
                }
            }
    }
}