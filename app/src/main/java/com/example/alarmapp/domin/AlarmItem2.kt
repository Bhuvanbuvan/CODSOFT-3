package com.example.alarmapp.domin

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import javax.annotation.processing.Generated

@Entity("alarmtb")
data class AlarmItem2(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val time:String,
    val message:String
)
