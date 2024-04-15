package com.example.alarmapp.domin

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DAOapi {
    @Insert
    suspend fun insert(alarmItem2: AlarmItem2)

    @Query("SELECT * FROM ALARMTB ORDER BY ID DESC")
    fun getalarm():Flow<List<AlarmItem2>>
}