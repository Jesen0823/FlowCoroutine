package com.jesen.pagingbookstore.db

import androidx.room.*
import com.jesen.pagingbookstore.model.VideoInfo

/**
 * 跟 实体类 VideoItem一样
 * 只是添加了一个字段 page,用来保存下一页的请求起点
 * */

@Entity
@TypeConverters(VideoInfoConverter::class)
data class VideoEntity(
    @PrimaryKey(autoGenerate = true)
    val keyInt:Int?,
    @ColumnInfo(name = "id_video")
    val id: Int,
    @ColumnInfo(name = "ad_index_video")
    val adIndex: Int?,
    @ColumnInfo(name = "m_video_info")
    val videoInfo: VideoInfo?,
    @ColumnInfo(name = "tag_video")
    val tag: String?,
    @ColumnInfo(name = "t_data_video")
    val trackingData: String?,
    @ColumnInfo(name = "type_video")
    val type: String?,

    // 自定义属性
    val page: Int = 1,
)