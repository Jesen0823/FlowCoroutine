package com.jesen.pagingbookstore.di

import com.jesen.pagingbookstore.db.AppDatabase
import com.jesen.pagingbookstore.mapper.Entity2RealModelMapper
import com.jesen.pagingbookstore.net.VideoListService
import com.jesen.pagingbookstore.repository.Repository
import com.jesen.pagingbookstore.repository.VideoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@InstallIn(ActivityComponent::class)
@Module
class RepositoryModel {

    @ActivityScoped
    @Provides
    fun provideVideoRepository(
        api:VideoListService,
        database: AppDatabase
    ):Repository{
        return VideoRepositoryImpl(api,database,Entity2RealModelMapper())
    }
}