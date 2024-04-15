package com.example.alarmapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.ZoneId

class AndroidAlamScheduler(
    private val context: Context
):AlarmScheuler {

    private val alarmManager=context.getSystemService(AlarmManager::class.java)

    override  fun sheduler(item: AlarmItem) {
        Log.i("TAG","done sucess ${item.time}")
            val intent=Intent(context,AlarmRecever::class.java).apply {
                putExtra("extra","${item.message}")
            }

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                PendingIntent.getBroadcast(
                    context,
                    item.hashCode(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            )


        Log.i("TAG","done sucess ${item.time}")
        }

}