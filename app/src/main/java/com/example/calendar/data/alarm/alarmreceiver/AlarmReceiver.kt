package com.example.calendar.data.alarm.alarmreceiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.core.app.NotificationCompat
import com.example.calendar.R

class AlarmReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("message")?:return
        showNotification(context!!,message)
        Log.d("AlarmReceiver", "onReceive: $message")
    }

    private fun showNotification(context: Context, message: String){
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelName = "reminder_channel"
        val channelId = "reminder_id"

        val channel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH,
        )
        manager.createNotificationChannel(channel)
        val builder = NotificationCompat.Builder(context,channelId)
            .setContentTitle("Reminder")
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_alarm_on_24)
        builder.apply {
            color = context.getColor(R.color.green)
        }
        manager.notify(message.hashCode(),builder.build())
    }
}