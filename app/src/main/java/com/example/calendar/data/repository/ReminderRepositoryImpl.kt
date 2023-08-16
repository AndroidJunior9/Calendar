package com.example.calendar.data.repository

import com.example.calendar.data.mappers.toReminder
import com.example.calendar.data.mappers.toReminderEntity
import com.example.calendar.data.model.ReminderDatabase
import com.example.calendar.domain.model.Reminder
import com.example.calendar.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ReminderRepositoryImpl(
    reminderDatabase: ReminderDatabase
): ReminderRepository {
    private val reminderDao = reminderDatabase.reminderDao
    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insert(reminder.toReminderEntity())
    }

    override fun getRemindersForDate(date: String): Flow<List<Reminder>> {
        return reminderDao.getRemindersForDate(date).map { reminders ->
            reminders.map { reminder ->
                reminder.toReminder()
            }
        }
    }

    override suspend fun deleteReminder(id: Int) {
        reminderDao.deleteReminder(id)
    }
}