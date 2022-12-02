package it.ncorti.emgvisualizer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

    }
}
