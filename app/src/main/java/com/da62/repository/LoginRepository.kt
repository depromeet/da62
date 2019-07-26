package com.da62.repository

import com.da62.datasource.api.ApiService
import com.da62.datasource.local.LoginLocalDataSource
import com.da62.model.Response
import com.da62.model.User
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

interface LoginRepository {

    fun postLogin(kakaoToken: String): Single<User>

    fun saveUser(user: User)

    fun postToken(accessToken: String, id: Int, token: String): Single<Response<Any>>
}

class LoginRepositoryImpl(
    private val apiService: ApiService,
    private val localDataSource: LoginLocalDataSource
) : LoginRepository {

    override fun postLogin(kakaoToken: String): Single<User> =
        apiService.postLogin(kakaoToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    override fun saveUser(user: User) = localDataSource.saveUser(user)

    override fun postToken(accessToken: String, id: Int, token: String): Single<Response<Any>> {
        return apiService.postDeviceToken(accessToken, id, token)
            .subscribeOn(Schedulers.io())
    }
}