package com.example.alarmapp.domin.repository

import com.example.alarmapp.domin.AlarmItem2
import com.example.alarmapp.domin.DAOapi
import kotlinx.coroutines.flow.Flow

class Reposito(private val daOapi: DAOapi) {
    suspend fun insertalam(alarmItem2: AlarmItem2){
        daOapi.insert(alarmItem2)
    }

    fun getalarm():Flow<List<AlarmItem2>>{
        return daOapi.getalarm()
    }
}