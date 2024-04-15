package com.example.alarmapp.notifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class SnoozeReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmItemId = intent?.getIntExtra("item_id", 0) ?: return
        // Handle snooze action
        // You can reschedule the alarm for a snooze interval or perform any other action
    }
}