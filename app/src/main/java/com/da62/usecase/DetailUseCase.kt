package com.da62.usecase

import com.da62.model.Plant
import com.da62.repository.DetailRepository
import io.reactivex.Single

interface DetailUseCase {

    fun getInfoList(): List<Plant>

    fun getDetail(id: Int): Single<Plant>

    fun postLove(id: Int): Single<Plant>
}

class DetailUseCaseImpl(private val repository: DetailRepository) : DetailUseCase {

    override fun getInfoList(): List<Plant> {
        val dummy = mutableListOf<Plant>()

        for (i in 0 until 4) {
            dummy.add(Plant())
        }

        return dummy
    }

    override fun getDetail(id: Int): Single<Plant> = repository.getDetail(id)

    override fun postLove(id: Int): Single<Plant> = repository.postLove(id)

}