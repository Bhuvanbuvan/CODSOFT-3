package com.example.alarmapp

import java.time.LocalDateTime

data class AlarmItem(
    val time:LocalDateTime,
    val message:String
)
