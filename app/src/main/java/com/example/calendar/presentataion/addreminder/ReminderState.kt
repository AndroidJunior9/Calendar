package com.example.calendar.presentataion.addreminder

import java.time.LocalDate
import java.time.LocalTime

data class ReminderState(
    val reminderText:String = "",
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val date: String = LocalDate.now().toString(),
    val time: String = LocalTime.NOON.toString(),
)
