package com.example.calendar.presentataion.addreminder

sealed class ReminderEvents{
    data class OnReminderTextChanged(val text: String): ReminderEvents()
    object OnSave: ReminderEvents()
    data class OnDateSelected(val date: String): ReminderEvents()
    data class OnTimeSelected(val time: String): ReminderEvents()
}
