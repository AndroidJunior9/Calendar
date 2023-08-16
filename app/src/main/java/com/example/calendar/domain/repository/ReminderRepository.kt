package com.example.calendar.domain.repository

import com.example.calendar.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun insertReminder(reminder: Reminder)
    fun getRemindersForDate(date: String): Flow<List<Reminder>>
    suspend fun deleteReminder(id: Int)
}