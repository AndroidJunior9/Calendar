package com.example.calendar.di

import androidx.room.Room
import com.example.calendar.data.alarm.scheduler.AndroidAlarmScheduler
import com.example.calendar.data.model.ReminderDatabase
import com.example.calendar.data.repository.ReminderRepositoryImpl
import com.example.calendar.domain.alarm.scheduler.AlarmScheduler
import com.example.calendar.domain.repository.ReminderRepository
import com.example.calendar.presentataion.addreminder.ReminderViewmodel
import com.example.calendar.presentataion.mainscreen.CalendarViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single{
        Room.databaseBuilder(
            androidContext(),
            ReminderDatabase::class.java,
            "reminder_database"
        ).build()
    }
    single<ReminderRepository> {
        ReminderRepositoryImpl(get())
    }
    single<AlarmScheduler> {
        AndroidAlarmScheduler(androidContext())
    }

    viewModel {
        CalendarViewmodel(get(),get())
    }

    viewModel {
        ReminderViewmodel(get(),get())
    }


}