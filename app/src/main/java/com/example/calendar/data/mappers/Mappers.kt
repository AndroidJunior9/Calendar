package com.example.calendar.data.mappers

import com.example.calendar.data.model.ReminderEntity
import com.example.calendar.domain.model.Reminder

fun ReminderEntity.toReminder(): Reminder {
    return Reminder(
        id = id,
        date = date,
        time = time,
        reminderText = reminderText,
        season = season
    )
}

fun Reminder.toReminderEntity(): ReminderEntity {
    return ReminderEntity(
        id = id,
        date = date,
        time = time,
        reminderText = reminderText,
        season = season
    )
}

