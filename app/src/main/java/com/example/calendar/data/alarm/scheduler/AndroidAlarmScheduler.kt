package com.example.calendar.data.alarm.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import com.example.calendar.data.alarm.alarmreceiver.AlarmReceiver
import com.example.calendar.domain.alarm.model.AlarmItem
import com.example.calendar.domain.alarm.scheduler.AlarmScheduler
import java.time.ZoneId

class AndroidAlarmScheduler(
    private val context: Context
):AlarmScheduler{

    private val alarmManager = context.getSystemService(AlarmManager::class.java)
    override fun scheduleAlarm(alarmItem: AlarmItem) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(AlarmManager::class.java)
            if (alarmManager?.canScheduleExactAlarms() == false) {
                // Your app does not have the permission to set exact alarms
                // Request the SCHEDULE_EXACT_ALARM permission
                Intent().also { intent ->
                    intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }
            }
        }

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("message", alarmItem.message)
        }
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                alarmItem.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    context,
                    alarmItem.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,

                )
            )


    }

    override fun cancelAlarm(alarmItem: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarmItem.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}