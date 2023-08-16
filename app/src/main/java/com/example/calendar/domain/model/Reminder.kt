package com.example.calendar.domain.model

data class Reminder(
    val id: Int? = null,
    val date: String,
    val time: String,
    val reminderText: String,
    val season: String
)
