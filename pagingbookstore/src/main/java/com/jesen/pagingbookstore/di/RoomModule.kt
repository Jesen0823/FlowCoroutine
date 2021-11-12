package com.jesen.pagingbookstore.di

import android.app.Application
import androidx.room.Room
import com.jesen.pagingbookstore.db.AppDatabase
import com.jesen.pagingbookstore.db.VideoDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDataBase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "video_store.db")
            .build()
    }

    @Singleton
    @Provides
    fun provideVideoDataDao(database: AppDatabase): VideoDataDao {
        return database.videoDataDao()
    }
}