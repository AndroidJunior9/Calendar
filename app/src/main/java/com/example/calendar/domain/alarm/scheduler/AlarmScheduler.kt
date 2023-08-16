package com.example.calendar.domain.alarm.scheduler

import com.example.calendar.domain.alarm.model.AlarmItem

interface AlarmScheduler {
    fun scheduleAlarm(alarmItem: AlarmItem)
    fun cancelAlarm(alarmItem: AlarmItem)
}