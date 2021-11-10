package com.jesen.flowcoroutine.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jesen.flowcoroutine.room.bean.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AbsDatabase:RoomDatabase() {
    abstract fun userDao():UserDao

    companion object{
        private var instance:AbsDatabase? = null

        fun getInstance(context: Context):AbsDatabase{
            return instance?: synchronized(this){
                Room.databaseBuilder(context, AbsDatabase::class.java,"user_info.db")
                    .build().also {
                        instance = it
                    }
            }
        }
    }
}