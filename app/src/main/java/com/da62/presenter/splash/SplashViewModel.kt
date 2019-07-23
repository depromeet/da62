package com.da62.presenter.splash

import androidx.lifecycle.LiveData
import com.da62.datasource.local.PreferenceStorage
import com.da62.presenter.base.BaseViewModel
import com.da62.util.SingleLiveEvent
import com.da62.util.add
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashViewModel(private val preferenceStorage: PreferenceStorage) : BaseViewModel() {

    private val _openToMain = SingleLiveEvent<Any>()
    val openToMain: LiveData<Any>
        get() = _openToMain

    private val _openToStart = SingleLiveEvent<Any>()
    val openToStart: LiveData<Any>
        get() = _openToStart

    init {
        compositeDisposable add Completable.timer(4500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ checkLogin() }, {})
    }

    private fun checkLogin() {
        if (preferenceStorage.isUserRegistered) {
            _openToMain.call()
        } else {
            _openToStart.call()
        }
    }
}