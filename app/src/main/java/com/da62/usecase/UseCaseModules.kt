package com.da62.usecase

import org.koin.dsl.module

val useCaseModules = module {
    factory { LoginUseCaseImpl(get()) as LoginUseCase }

    factory { PlantRegistUseCaseImpl(get()) as PlantRegistUseCase }

    factory { DetailUseCaseImpl(get()) as DetailUseCase }

    factory { MainUseCaseImpl(get()) as MainUseCase }

    factory { GalleryUseCaseImpl(get(), get()) as GalleryUseCase }
}