package com.da62.usecase

import com.da62.model.SavePlant
import com.da62.repository.PlantRegistRepository
import io.reactivex.Observable
import io.reactivex.Single

interface PlantRegistUseCase {

    fun getPlantNames(token: String, input: String): Observable<List<String>>

    fun savePlant(token: String, savePlant: SavePlant, userId: Int): Single<String>
}

class PlantRegistUseCaseImpl(private val repository: PlantRegistRepository) : PlantRegistUseCase {

    override fun getPlantNames(token: String, input: String): Observable<List<String>> =
        repository.getPlantNames(token, input)

    override fun savePlant(token: String, savePlant: SavePlant, userId: Int): Single<String> =
        repository.savePlant(token, savePlant, userId)
}