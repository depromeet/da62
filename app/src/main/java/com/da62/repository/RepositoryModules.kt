package com.da62.repository

import org.koin.dsl.module

val repositoryModules = module {
    factory { LoginRepositoryImpl(get(), get()) as LoginRepository }

    factory { PlantRegistRepositoryImpl(get()) as PlantRegistRepository }

    factory { DetailRepositoryImpl(get(), get()) as DetailRepository }

    factory { MainRepositoryImpl(get(), get()) as MainRepository }

    factory { GalleryRepositoryImpl(get(), get()) as GalleryRepository }
}