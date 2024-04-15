package com.example.alarmapp

import android.Manifest
import android.app.AlertDialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.alarmapp.notifi.DismissReceiver
import com.example.alarmapp.notifi.SnoozeReceiver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmRecever:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val message= intent?.getStringExtra("extra") ?:return
        println("Alarm Triger: $message")
        Log.i("TAG","Alarm receved")
        val alarmItemId = intent.getIntExtra("item_id", 0)
       /* Toast.makeText(context, "Alarm received", Toast.LENGTH_SHORT).show()*/

        // Show notification with snooze and dismiss actions
        val notification = createNotification(context, message, alarmItemId)
        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        NotificationManagerCompat.from(context!!).notify(alarmItemId, notification.build())

    }

    private fun createNotification(context: Context?, message: String, alarmItemId: Int): NotificationCompat.Builder {
        val snoozeIntent = Intent(context, SnoozeReceiver::class.java).apply {
            action = "SNOOZE_ACTION"
            putExtra("item_id", alarmItemId)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(context, alarmItemId, snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val dismissIntent = Intent(context, DismissReceiver::class.java).apply {
            action = "DISMISS_ACTION"
            putExtra("item_id", alarmItemId)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(context, alarmItemId, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        return NotificationCompat.Builder(context!!, "channel_id")
            .setContentTitle("Alarm Triggered")
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_alarm_24)
            .addAction(R.drawable.baseline_snooze_24, "Snooze", snoozePendingIntent)
            .addAction(R.drawable.baseline_do_disturb_alt_24, "Dismiss", dismissPendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
    }


}

