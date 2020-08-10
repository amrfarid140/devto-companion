package me.amryousef.devto

import android.app.Application
import executor

class DEVApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        executor = CoroutineExecutor()
    }
}