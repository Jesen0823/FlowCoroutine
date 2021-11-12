package com.jesen.pagingbookstore.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface VideoDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideoData(videoList:List<VideoEntity>)

    @Query("SELECT * FROM VideoEntity")
    fun getVideos():PagingSource<Int, VideoEntity>


    @Query("DELETE FROM VideoEntity")
    suspend fun cleatVideos()

}