package com.da62.datasource.local

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModules = module {

    factory {
        LoginLocalDataSourceImpl() as LoginLocalDataSource
    }

    factory { SharedPreferenceStorage(androidContext()) as PreferenceStorage }
}