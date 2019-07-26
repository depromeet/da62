package com.da62.presenter.splash

import androidx.lifecycle.LiveData
import com.da62.datasource.local.PreferenceStorage
import com.da62.presenter.base.BaseViewModel
import com.da62.util.SingleLiveEvent

class SplashViewModel(private val preferenceStorage: PreferenceStorage) : BaseViewModel() {

    private val _openToMain = SingleLiveEvent<Any>()
    val openToMain: LiveData<Any>
        get() = _openToMain

    private val _openToStart = SingleLiveEvent<Any>()
    val openToStart: LiveData<Any>
        get() = _openToStart

    fun checkLogin() {
        if (preferenceStorage.isUserRegistered) {
            _openToMain.call()
        } else {
            _openToStart.call()
        }
    }
}