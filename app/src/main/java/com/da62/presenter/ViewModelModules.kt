package com.da62.presenter

import com.da62.presenter.detail.DetailViewModel
import com.da62.presenter.gallery.GalleryViewModel
import com.da62.presenter.login.LoginViewModel
import com.da62.presenter.main.MainViewModel
import com.da62.presenter.splash.SplashViewModel
import com.da62.presenter.write.plant.PlantRegistViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { PlantRegistViewModel(get(), get()) }
    viewModel { GalleryViewModel(get()) }
}