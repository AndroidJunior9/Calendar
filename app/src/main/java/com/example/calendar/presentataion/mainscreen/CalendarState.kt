package com.example.calendar.presentataion.mainscreen

import com.example.calendar.domain.model.Reminder
import java.time.LocalDate

data class CalendarState(
    val reminders: List<Reminder> = emptyList(),
    val selectedDate: String = LocalDate.now().toString(),
)
