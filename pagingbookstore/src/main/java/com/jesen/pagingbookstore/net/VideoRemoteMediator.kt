package com.jesen.pagingbookstore.net

import android.util.Log
import android.widget.Toast
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.PrimaryKey
import androidx.room.withTransaction
import com.google.gson.annotations.SerializedName
import com.jesen.pagingbookstore.binding.isConnectedNet
import com.jesen.pagingbookstore.binding.parseNextPageStart
import com.jesen.pagingbookstore.db.AppDatabase
import com.jesen.pagingbookstore.db.VideoEntity
import com.jesen.pagingbookstore.init.AppHelper
import com.jesen.pagingbookstore.model.VideoItem

@ExperimentalPagingApi
class VideoRemoteMediator(
    private val api: VideoListService,
    private val database: AppDatabase
) : RemoteMediator<Int, VideoEntity>() {

    companion object {
        const val TAG = "VideoRemoteMediator"
    }

    override suspend fun load(
        loadType: LoadType,

        /// PagingState 有两个值：config(用来配置每页多少首次加载多少)，pages:上一页数据
        state: PagingState<Int, VideoEntity>
    ): MediatorResult {
        try {
            Log.d(TAG, "===load,loadType: $loadType")

            /**
             * 第一步，判断 LoadType计算当前page
             */
            val pageKey = when (loadType) {
                // 首次访问或者下拉刷新 调用PagingDataAdapter.refresh()
                LoadType.REFRESH -> null
                // 列表开头加载数据时,REFRESH完了会调用PREPEND准备数据
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                // 上拉加载更多
                LoadType.APPEND -> {
                    // lastItemOrNull 是上一页最后一条数据
                    val lastItem: VideoEntity =
                        state.lastItemOrNull() ?: return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    // 上一页页码开始位置
                    Log.d(TAG, "===load,lastItem.page: ${lastItem.page}")
                    lastItem.page
                }
            }

            // 如果网络异常，加载本地数据
            if(!AppHelper.mContext.isConnectedNet()){
                Toast.makeText(AppHelper.mContext,"网络异常！加载本地数据",Toast.LENGTH_SHORT).show()
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            //第二步，请求网络分页数据
            //val curPageStart = pageKey ?: 0
            val curPageStart = pageKey ?: 1

            val result = api.getVideoList(
                // 此处不用计算下次请求的起始位置，因为curPageStart就是上次保存的，这次的起始位置
                //itemStart = curPageStart * state.config.pageSize,
                itemStart = curPageStart,
                pageSize = if (pageKey == null) state.config.initialLoadSize else state.config.pageSize
            )
            Log.d(TAG, "===load,curPageStart   : $curPageStart")

            // 转换数据,从VideoItem转成VideoEntity,便于存入数据库
            val realNextStart = result.nextPageUrl.parseNextPageStart()
            Log.d(TAG, "===load,realNextStart from 'nextPageUrl' : $realNextStart")
            val videoEntityList = result.videoList?.map {
                VideoEntity(
                    keyInt = null,
                    id = it.id ?: 0,
                    adIndex = it.adIndex ?: 0,
                    videoInfo = it.videoInfo ?: null,
                    tag = it.tag ?: "",
                    trackingData = it.trackingData ?: "",
                    type = it.type ?: "",
                    // 因为我们使用的接口分页规则，下一页不是按上一页最后一个Item来的
                    //page = curPageStart + 1
                    // 当前接口分页规则：下一页起点已经是上页返回中已经确定了的
                    page = realNextStart
                )
            }

            //第三步，插入数据库。
            val videoDatabase = database.videoDataDao()
            // 作为事务提交，保证同时成功同时失败
            database.withTransaction {
                // 如果是首次请求或刷新
                if (loadType == LoadType.REFRESH) {
                    videoDatabase.cleatVideos()
                }
                // 请求结果插入数据库
                if (videoEntityList != null) {
                    Log.d("Media===", "videoEntityList size: ${videoEntityList.size}")
                    videoDatabase.insertVideoData(videoEntityList)
                }
            }

            // 是否有数据
            val endOfPaginationReached = result.videoList?.isEmpty() ?: false
            Log.d(TAG, "===load,endOfPaginationReached: $endOfPaginationReached")
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            e.printStackTrace()
            return MediatorResult.Error(e)
        }
    }

}