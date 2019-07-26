package com.da62.usecase

import com.da62.model.Response
import com.da62.model.User
import com.da62.repository.LoginRepository
import io.reactivex.Single

interface LoginUseCase {

    fun login(kakaoToken: String): Single<User>

    fun saveUser(user: User)

    fun postToken(accessToken: String, id: Int, token: String): Single<Response<Any>>
}

class LoginUseCaseImpl(private val repository: LoginRepository) : LoginUseCase {

    override fun login(kakaoToken: String) = repository.postLogin(kakaoToken)

    override fun saveUser(user: User) = repository.saveUser(user)

    override fun postToken(accessToken: String, id: Int, token: String): Single<Response<Any>> {
        return repository.postToken(accessToken, id, token)
    }
}