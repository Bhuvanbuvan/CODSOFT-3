package com.example.alarmapp.domin


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AlarmItem2::class], version = 1)
abstract class Rooddb :RoomDatabase(){
    abstract fun getado():DAOapi
    companion object{
        @Volatile
        var INSTENCE:Rooddb?=null

        fun getinsetence(context: Context):Rooddb{
            synchronized(this){
                var instence= INSTENCE
                if (instence==null){
                    instence= Room.databaseBuilder(
                        context.applicationContext,
                        Rooddb::class.java,
                        "Roomdb"
                    ).build()
                }
                return instence
            }
        }
    }
}