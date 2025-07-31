package com.tigasatudesember.bukupemasok

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class RootApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

    }
}