package com.da62.datasource.local

import com.da62.util.PermissionProvider
import com.da62.util.PermissionProviderImpl
import com.da62.util.ResourceProvider
import com.da62.util.ResourceProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModules = module {

    factory {
        LoginLocalDataSourceImpl() as LoginLocalDataSource
    }

    factory { SharedPreferenceStorage(androidContext()) as PreferenceStorage }

    factory { PermissionProviderImpl(androidContext()) as PermissionProvider }

    factory { ResourceProviderImpl(androidContext()) as ResourceProvider }
}