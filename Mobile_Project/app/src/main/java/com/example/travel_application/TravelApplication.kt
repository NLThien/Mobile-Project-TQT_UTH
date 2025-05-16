package com.example.travel_application

import android.app.Application
import com.example.travel_application.accessibility.ServiceLocator
import dagger.hilt.android.HiltAndroidApp
import com.google.android.gms.maps.MapsInitializer
import android.os.StrictMode
import com.google.firebase.BuildConfig

@HiltAndroidApp
class TravelApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        // Chỉ bật StrictMode trong debug build
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll() // Phát hiện tất cả vấn đề trên main thread
//                    .detectDiskReads()
//                    .detectDiskWrites()
//                    .detectNetwork()
                    .penaltyLog() // Chỉ log chứ không crash app
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedSqlLiteObjects() // Rò rỉ SQLite
                    .detectLeakedClosableObjects() // Rò rỉ Closeable
                    .penaltyLog()
                    .build()
            )
        }

        ServiceLocator
        MapsInitializer.initialize(this)
    }
}