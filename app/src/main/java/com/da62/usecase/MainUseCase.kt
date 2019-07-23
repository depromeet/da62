package com.da62.usecase

import com.da62.model.Plant
import com.da62.repository.MainRepository
import io.reactivex.Single

interface MainUseCase {

    fun getPlantList(): Single<List<Plant>>
}

class MainUseCaseImpl(private val repository: MainRepository) : MainUseCase {

    override fun getPlantList(): Single<List<Plant>> = repository.getPlantList()
}