package com.da62.repository

import com.da62.datasource.api.ApiService
import com.da62.datasource.local.PreferenceStorage
import com.da62.model.Plant
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

interface MainRepository {

    fun getPlantList(): Single<List<Plant>>

    fun postLove(id: Int): Single<Plant>
}

class MainRepositoryImpl(
    private val apiService: ApiService,
    private val preferenceStorage: PreferenceStorage
) : MainRepository {

    override fun getPlantList(): Single<List<Plant>> =
        apiService.getPlants(
            accessToken = preferenceStorage.accessToken ?: "",
            userId = preferenceStorage.userId
        ).subscribeOn(Schedulers.io())

    override fun postLove(id: Int): Single<Plant> {
        return apiService.postLove(
            accessToken = preferenceStorage.accessToken ?: "",
            id = id
        ).subscribeOn(Schedulers.io())
    }
}