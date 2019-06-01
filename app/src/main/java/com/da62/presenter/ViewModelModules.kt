package com.da62.presenter

import com.da62.presenter.login.LoginViewModel
import com.da62.presenter.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { SplashViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel() }
}