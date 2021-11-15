package com.jesen.pagingbookstore.repository

import androidx.paging.*
import com.jesen.pagingbookstore.db.AppDatabase
import com.jesen.pagingbookstore.db.VideoEntity
import com.jesen.pagingbookstore.mapper.Mapper
import com.jesen.pagingbookstore.model.VideoItem
import com.jesen.pagingbookstore.model.VideoStore
import com.jesen.pagingbookstore.net.VideoListService
import com.jesen.pagingbookstore.net.VideoRemoteMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class VideoRepositoryImpl(
    private val api: VideoListService,
    private val database: AppDatabase,
    private val mapper2RealModel: Mapper<VideoEntity, VideoItem>,
) : Repository {

    /**
     * remoteMediator去请求网络数据
     * */
    @ExperimentalPagingApi
    override fun fetchVideoList(): Flow<PagingData<VideoItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 6,
                prefetchDistance = 1,
                initialLoadSize = 12
            ),
            // 请求完网络数据放在数据库
            remoteMediator = VideoRemoteMediator(api, database)
        ) {
            // 从数据库拿到的是pagingData
            database.videoDataDao().getVideos()

        }.flow.flowOn(Dispatchers.IO)
            .map { pagingData ->
                // 将pagingData中的VideoEntity转换为真实JavaBean
                pagingData.map { mapper2RealModel.map(it) }
            }
    }
}