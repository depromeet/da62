package com.da62.usecase

import com.da62.model.Plant
import com.da62.repository.MainRepository
import io.reactivex.Single

interface MainUseCase {

    fun getPlantList(): Single<List<Plant>>

    fun postLove(id: Int): Single<Plant>
}

class MainUseCaseImpl(private val repository: MainRepository) : MainUseCase {

    override fun getPlantList(): Single<List<Plant>> = repository.getPlantList()

    override fun postLove(id: Int): Single<Plant> = repository.postLove(id)
}