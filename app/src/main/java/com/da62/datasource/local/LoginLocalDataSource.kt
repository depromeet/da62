package com.da62.datasource.local

import com.da62.model.User

interface LoginLocalDataSource {

    fun saveUser(user: User)
}

class LoginLocalDataSourceImpl : LoginLocalDataSource {

    companion object {
        var user: User? = null
    }

    override fun saveUser(user: User) {
        LoginLocalDataSourceImpl.user = user
    }
}