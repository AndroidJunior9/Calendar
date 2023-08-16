package com.example.calendar.presentataion.addreminder

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.domain.alarm.model.AlarmItem
import com.example.calendar.domain.alarm.scheduler.AlarmScheduler
import com.example.calendar.domain.model.Reminder
import com.example.calendar.domain.repository.ReminderRepository
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class ReminderViewmodel(
    private val reminderRepository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler
):ViewModel() {

    var state by mutableStateOf(ReminderState())
    private set

    fun onEvent(event: ReminderEvents) {
        when (event) {
            is ReminderEvents.OnReminderTextChanged -> {
                state = state.copy(reminderText = event.text)
            }
            is ReminderEvents.OnDateSelected -> {
                state = state.copy(date = event.date)
            }
            is ReminderEvents.OnTimeSelected -> {
                state = state.copy(time = event.time)
            }
            is ReminderEvents.OnSave -> {
                viewModelScope.launch {
                    Log.d("ReminderViewmodel", "onEvent: ${state.date}")
                    reminderRepository.insertReminder(
                        Reminder(
                            reminderText = state.reminderText,
                            date = state.date,
                            time = state.time,
                            season = getSeason(state.date)
                        )
                    )
                }
                val date = java.time.LocalDate.parse(state.date)
                val time = LocalTime.parse(state.time)
                val dateTime = LocalDateTime.of(date, time)

                alarmScheduler.scheduleAlarm(
                    AlarmItem(
                        time = dateTime,
                        message = state.reminderText)
                )
            }
        }
    }

    private fun getSeason(date:String): String {
        val seasons = listOf("Spring","Summer","Monsoon","Autumn","Winter")
        return when(LocalDate.parse(date).dayOfYear){
            in 40..75 -> seasons[0]
            in 75..154 -> seasons[1]
            in 154..279 -> seasons[2]
            in 279..325 -> seasons[3]
            else -> seasons[4]
        }
    }

}