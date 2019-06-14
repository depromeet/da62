package com.da62.usecase

import com.da62.repository.LoginRepository
import io.reactivex.Single

interface LoginUseCase {

    fun login(accessToken: String): Single<Int>
}

class LoginUseCaseImpl(private val mRepository: LoginRepository) : LoginUseCase {

    override fun login(accessToken: String) =
        mRepository.postKakaoProfile(accessToken)
}