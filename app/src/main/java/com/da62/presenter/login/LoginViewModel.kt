package com.da62.presenter.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.da62.datasource.local.PreferenceStorage
import com.da62.presenter.base.BaseViewModel
import com.da62.usecase.LoginUseCase
import com.da62.util.SingleLiveEvent
import com.da62.util.add
import com.google.firebase.iid.FirebaseInstanceId
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.util.exception.KakaoException
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers

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

            .doOnSuccess {
                useCase.saveUser(it)
                preferenceStorage.accessToken = it.token
                preferenceStorage.userId = it.userId
                preferenceStorage.isUserRegistered = true
            }
            .flatMap {
                Single.create<String> { emitter ->
                    FirebaseInstanceId.getInstance().instanceId
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                it.result?.token?.let { token ->
                                    emitter.onSuccess(token)
                                } ?: emitter.onError(Throwable("Missing Token!"))

                            } else {
                                emitter.onError(Throwable("Missing Token!"))
                            }
                        }
                }
            }
            .flatMap {
                useCase.postToken(
                    preferenceStorage.accessToken ?: "",
                    preferenceStorage.userId,
                    it
                )
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _progress.value = false
                _login.call()
            }, {
                _progress.postValue(false)
                _error.postValue("서버에러입니다. 다시 시도해주세요.")
            })
    }

    private fun postFcmToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.token?.let { token ->
                    }
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        Session.getCurrentSession().removeCallback(kakaoCallback)
    }
}