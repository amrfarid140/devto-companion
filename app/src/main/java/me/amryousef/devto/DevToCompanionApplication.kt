package me.amryousef.devto

import android.app.Application
import me.amryousef.devto.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DevToCompanionApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DevToCompanionApplication)
            modules(applicationModule)
        }
    }
}