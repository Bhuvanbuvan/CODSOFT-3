package com.example.alarmapp.notifi

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class DismissReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmItemId = intent?.getIntExtra("item_id", 0) ?: return
        // Handle dismiss action
        // You can cancel the alarm or perform any other action
    }
}