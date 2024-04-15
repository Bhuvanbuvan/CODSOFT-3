package com.example.alarmapp.domin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmapp.domin.AlarmItem2
import com.example.alarmapp.domin.repository.Reposito
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ViewModel(private val repo:Reposito) :ViewModel(){
    fun insalam(alarmItem2: AlarmItem2){
        viewModelScope.launch {
            repo.insertalam(alarmItem2)
        }
    }

    fun getalrm():Flow<List<AlarmItem2>>{
        return repo.getalarm()
    }
}