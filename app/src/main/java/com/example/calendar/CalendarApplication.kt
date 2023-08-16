package com.example.calendar

import android.app.Application
import com.example.calendar.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class CalendarApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@CalendarApplication)
            modules(appModule)

        }
    }
}