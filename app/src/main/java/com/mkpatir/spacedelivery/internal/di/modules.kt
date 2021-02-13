package com.mkpatir.spacedelivery.internal.di

import com.mkpatir.spacedelivery.api.AppServiceFactory
import org.koin.dsl.module

val appModule = module(true) {
    factory {
        AppServiceFactory.buildService()
    }
}

val viewModelModule = module(true) {

}

val appModules = listOf(appModule, viewModelModule)