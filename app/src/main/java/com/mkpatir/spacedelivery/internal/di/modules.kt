package com.mkpatir.spacedelivery.internal.di

import com.mkpatir.spacedelivery.api.AppRepository
import com.mkpatir.spacedelivery.api.AppRepositoryImpl
import com.mkpatir.spacedelivery.api.AppServiceFactory
import com.mkpatir.spacedelivery.internal.utils.PreferenceHelper
import com.mkpatir.spacedelivery.ui.base.EmptyViewModel
import com.mkpatir.spacedelivery.ui.home.HomeViewModel
import com.mkpatir.spacedelivery.ui.spaceship.SpaceShipViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module(true) {

    single { PreferenceHelper(androidContext()) }

    factory<AppRepository> {
        AppRepositoryImpl(
            get()
        )
    }

    factory {
        AppServiceFactory.buildService()
    }
}

val viewModelModule = module(true) {
    viewModel { EmptyViewModel() }
    viewModel { SpaceShipViewModel(get()) }
    viewModel { HomeViewModel(get(),get()) }
}

val appModules = listOf(appModule, viewModelModule)