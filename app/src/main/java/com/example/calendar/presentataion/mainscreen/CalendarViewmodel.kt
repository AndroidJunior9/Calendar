package com.example.calendar.presentataion.mainscreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendar.domain.alarm.model.AlarmItem
import com.example.calendar.domain.alarm.scheduler.AlarmScheduler
import com.example.calendar.domain.repository.ReminderRepository
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime

class CalendarViewmodel(
    private val reminderRepository: ReminderRepository,
    private val alarmScheduler: AlarmScheduler
):ViewModel() {

    var state by mutableStateOf(CalendarState())
        private set

    init{
        onEvent(CalendarEvents.OnDateSelected(state.selectedDate))
    }

    fun onEvent(event: CalendarEvents) {
        when (event) {
            is CalendarEvents.OnDateSelected -> {
                state = state.copy(selectedDate = event.date)
                viewModelScope.launch {
                    reminderRepository.getRemindersForDate(event.date).collect { reminders ->
                        Log.d("CalendarViewmodel", "onEvent: $reminders")
                        state = state.copy(reminders = reminders)
                    }
                }
            }

            is CalendarEvents.OnReminderDeleted -> {
                val date = java.time.LocalDate.parse(event.reminder.date)
                val time = LocalTime.parse(event.reminder.time)
                val dateTime = LocalDateTime.of(date, time)
                alarmScheduler.cancelAlarm(AlarmItem(
                    time = dateTime,
                    message = event.reminder.reminderText
                ))
                viewModelScope.launch {
                    reminderRepository.deleteReminder(event.reminder.id!!)
                    reminderRepository.getRemindersForDate(state.selectedDate).collect { reminders ->
                        state = state.copy(reminders = reminders)
                    }
                }

            }



        }
    }
}