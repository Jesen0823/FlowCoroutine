package com.jesen.pagingbookstore.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * 数据库操作类
 * */

@Dao
interface VideoDataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = VideoEntity::class)
    suspend fun insertVideoData(videoList:List<VideoEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE, entity = VideoEntity::class)
    suspend fun insertVideoData(vararg args:VideoEntity)

    @Query("SELECT * FROM VideoEntity")
    fun getVideos():PagingSource<Int, VideoEntity>


    @Query("DELETE FROM VideoEntity")
    suspend fun cleatVideos()

}