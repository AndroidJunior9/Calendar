package com.example.calendar.presentataion.mainscreen

import com.example.calendar.domain.model.Reminder

sealed class CalendarEvents{
    data class OnDateSelected(val date: String): CalendarEvents()
    data class OnReminderDeleted(val reminder: Reminder): CalendarEvents()
}
