package com.example.travel_application

import android.app.Application
import com.example.travel_application.accessibility.ServiceLocator
import dagger.hilt.android.HiltAndroidApp
import com.google.android.gms.maps.MapsInitializer

@HiltAndroidApp
class TravelApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Khởi tạo ServiceLocator
        ServiceLocator
        MapsInitializer.initialize(this)
    }
}