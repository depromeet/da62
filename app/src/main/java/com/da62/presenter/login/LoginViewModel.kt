package com.da62.presenter.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.da62.datasource.local.PreferenceStorage
import com.da62.presenter.base.BaseViewModel
import com.da62.usecase.LoginUseCase
import com.da62.util.SingleLiveEvent
import com.da62.util.add
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException

class LoginViewModel(
    private val useCase: LoginUseCase,
    private val preferenceStorage: PreferenceStorage
) : BaseViewModel() {

    private val _error = SingleLiveEvent<String>()

    private val _login = SingleLiveEvent<Any>()

    private val _progress = MutableLiveData<Boolean>()

    val error: LiveData<String> = _error

    val login: LiveData<Any> = _login

    val progress: LiveData<Boolean> = _progress

    private val kakaoCallback = object : ISessionCallback {

        override fun onSessionOpenFailed(exception: KakaoException?) {
            _error.value = "로그인에 실패했습니다. 다시 시도해주세요."
        }

        override fun onSessionOpened() {
            // 세션은 변경될 수 있음.
            val token = Session.getCurrentSession().tokenInfo.accessToken
            login(token)
        }
    }

    init {
        Session.getCurrentSession().addCallback(kakaoCallback)
        Session.getCurrentSession().checkAndImplicitOpen()
    }

    private fun login(accessToken: String) {
        _progress.value = true
        compositeDisposable add useCase.login(accessToken)
            .subscribe({
                _progress.value = false
                useCase.saveUser(it)
                preferenceStorage.accessToken = it.token
                preferenceStorage.userId = it.userId
                preferenceStorage.isUserRegistered = true
                _login.call()
            }, {
                _progress.value = false
                _error.value = "서버에러입니다. 다시 시도해주세요."
            })
    }

    override fun onCleared() {
        super.onCleared()
        Session.getCurrentSession().removeCallback(kakaoCallback)
    }
}