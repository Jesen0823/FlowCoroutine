package com.jesen.pagingbookstore.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.jesen.pagingbookstore.db.*

data class VideoStore(
    val adExist: Boolean,
    val count: Int,
    @SerializedName("itemList")
    val videoList: List<VideoItem>?,
    val nextPageUrl: String,
    val total: Int
)

@Entity
@TypeConverters(VideoInfoConverter::class)
data class VideoItem(
    val adIndex: Int?,
    @SerializedName("data")
    val videoInfo: VideoInfo?,
    val id: Int,
    val tag: String?,
    val trackingData: String?,
    val type: String?
)

@Entity
@TypeConverters(
    StringTypeConverter::class,
    AuthorConverter::class,
    ConsumptionConverter::class,
    CoverConverter::class,
    PlayInfoTypeConverter::class,
    ProviderConverter::class,
    TagTypeConverter::class,
    WebUrlConverter::class
)
data class VideoInfo(
    val ad: Boolean = false,

    val adTrack: List<String>?,
    //@TypeConverters(AuthorConverter::class)
    val author: Author?,
    val brandWebsiteInfo: String?,
    val campaign: String?,
    val category: String?,
    val collected: Boolean = false,
    //@TypeConverters(ConsumptionConverter::class)
    val consumption: Consumption?,
    //@TypeConverters(CoverConverter::class)
    val cover: Cover?,
    val dataType: String?,
    val date: Long?,
    @ColumnInfo(name = "v_info_description")
    val description: String?,
    val descriptionEditor: String?,
    val descriptionPgc: String?,
    val duration: Int?,
    val favoriteAdTrack: String?,
    @PrimaryKey
    @ColumnInfo(name = "id_info")
    val id: Int?,
    val idx: Int?,
    val ifLimitVideo: Boolean = false,
    val label: String?,
    //@TypeConverters(StringTypeConverter::class)
    val labelList: List<String>?,
    val lastViewTime: String?,
    val library: String?,
    //@TypeConverters(PlayInfoTypeConverter::class)
    val playInfo: List<PlayInfo>?,
    val playUrl: String?,
    val played: Boolean = false,
    // @TypeConverters(PlayInfoTypeConverter::class)
    val playlists: List<PlayInfo>?,
    val promotion: String?,
    //@TypeConverters(ProviderConverter::class)
    val provider: Provider?,
    val reallyCollected: Boolean?,
    val recallSource: String?,
    val recall_source: String?,
    val releaseTime: Long?,
    val remark: String?,
    val resourceType: String?,
    val searchWeight: Int?,
    val shareAdTrack: String?,
    val slogan: String?,
    val src: String?,
    //@TypeConverters(StringTypeConverter::class)
    val subtitles: List<String>?,
    //@TypeConverters(TagTypeConverter::class)
    val tags: List<Tag>?,
    val thumbPlayUrl: String?,
    val title: String?,
    val titlePgc: String?,
    @ColumnInfo(name = "v_info_type")
    val type: String?,
    val videoPosterBean: String?,
    val waterMarks: String?,
    val webAdTrack: String?,
    //@TypeConverters(WebUrlConverter::class)
    val webUrl: WebUrl?
)

@Entity
@TypeConverters(FollowConverter::class, ShieldConverter::class)
data class Author(
    @ColumnInfo(name = "author_ad_track")
    val adTrack: String?,
    val approvedNotReadyVideoCount: Int,
    @ColumnInfo(name = "author_desc")
    val description: String,
    val expert: Boolean,
    //@TypeConverters(FollowConverter::class)
    val follow: Follow?,
    val icon: String,
    @PrimaryKey
    @ColumnInfo(name = "author_id")
    val id: Int,
    val ifPgc: Boolean,
    val latestReleaseTime: Long,
    val link: String,
    @ColumnInfo(name = "author_name")
    val name: String,
    val recSort: Int,
    //@TypeConverters(ShieldConverter::class)
    val shield: Shield?,
    val videoNum: Int
)

@Entity
data class Consumption(
    @PrimaryKey
    val collectionCount: Int,
    val realCollectionCount: Int,
    val replyCount: Int,
    val shareCount: Int
)

@Entity
data class Cover(
    @PrimaryKey
    val blurred: String,
    val detail: String,
    val feed: String,
    val homepage: String?,
    val sharing: String?
)

@Entity
@TypeConverters(UrlTypeConverter::class)
data class PlayInfo(
    val height: Int,
    @PrimaryKey
    @ColumnInfo(name = "info_name")
    val name: String,
    @ColumnInfo(name = "type_play_info")
    val type: String,
    @ColumnInfo(name = "info_url")
    val url: String,
    //@TypeConverters(UrlTypeConverter::class)
    val urlList: List<Url>?,
    val width: Int
)

@Entity
data class Provider(
    val alias: String,
    @ColumnInfo(name = "provider_icon")
    val icon: String,
    @PrimaryKey
    @ColumnInfo(name = "provider_name")
    val name: String
)

@Entity
@TypeConverters(StringTypeConverter::class)
data class Tag(
    val actionUrl: String,
    @ColumnInfo(name = "tag_ad_track")
    val adTrack: String?,
    val bgPicture: String,
    //@TypeConverters(StringTypeConverter::class)
    val childTagIdList: List<String>?,
    //@TypeConverters(StringTypeConverter::class)
    val childTagList: List<String>?,
    val communityIndex: Int,
    @ColumnInfo(name = "tag_desc")
    val desc: String,
    val haveReward: Boolean,
    val headerImage: String,
    @PrimaryKey
    @ColumnInfo(name = "tag_id")
    val id: Int,
    val ifNewest: Boolean,
    @ColumnInfo(name = "tag_name")
    val name: String,
    val newestEndTime: String?,
    val tagRecType: String
)

@Entity
data class WebUrl(
    @PrimaryKey
    val forWeibo: String,
    val raw: String
)

@Entity
data class Follow(
    val followed: Boolean,
    @PrimaryKey
    val itemId: Int,
    val itemType: String
)

@Entity
data class Shield(
    @PrimaryKey
    @ColumnInfo(name = "shield_itemId")
    val itemId: Int,
    @ColumnInfo(name = "shield_item_t")
    val itemType: String,
    val shielded: Boolean
)

@Entity
data class Url(
    @PrimaryKey
    @ColumnInfo(name = "url_name")
    val name: String,
    val size: Int,
    @ColumnInfo(name = "url_c")
    val url: String
)