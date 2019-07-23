package com.da62.repository

import com.da62.datasource.api.ApiService
import com.da62.model.SavePlant
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface PlantRegistRepository {

    fun getPlantNames(token: String, input: String): Observable<List<String>>

    fun savePlant(token: String, savePlant: SavePlant, userId: Int): Single<String>
}

class PlantRegistRepositoryImpl(private val apiService: ApiService) : PlantRegistRepository {

    override fun getPlantNames(token: String, input: String): Observable<List<String>> =
        apiService.getPlantNames(token, input)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun savePlant(token: String, savePlant: SavePlant, userId: Int): Single<String> =
        apiService.savePlant(token, plantDto = savePlant, userId = userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}