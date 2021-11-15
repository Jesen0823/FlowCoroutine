package com.jesen.pagingbookstore.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [VideoEntity::class],
    version = 1, exportSchema = false
)

abstract class AppDatabase :RoomDatabase(){
    abstract fun videoDataDao():VideoDataDao
}