package com.mkpatir.spacedelivery

import android.app.Application
import com.mkpatir.spacedelivery.internal.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceDeliveryApp: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpaceDeliveryApp)
            modules(appModules)
        }
    }

}