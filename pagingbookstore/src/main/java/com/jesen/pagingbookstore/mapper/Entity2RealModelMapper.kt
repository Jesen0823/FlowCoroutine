package com.jesen.pagingbookstore.mapper

import android.util.Log
import com.jesen.pagingbookstore.db.VideoEntity
import com.jesen.pagingbookstore.model.VideoItem

/**
 * 将数据库中存储的对象转换为 javaBean
 * */
class Entity2RealModelMapper : Mapper<VideoEntity, VideoItem> {

    override fun map(input: VideoEntity): VideoItem {
        Log.d("RealModelMapper--","input:${input.videoInfo}")
        return VideoItem(
            id = input.id,
            adIndex = input.adIndex,
            videoInfo = input.videoInfo,
            tag = input.tag,
            trackingData = input.trackingData,
            type = input.type,
        )
    }
}