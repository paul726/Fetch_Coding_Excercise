package com.pjiang.fetch_coding_exercise

import android.app.Application
import com.pjiang.fetch_coding_exercise.modules.modules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@AppApplication)
            // Load modules
            modules(modules)
        }
    }
}

