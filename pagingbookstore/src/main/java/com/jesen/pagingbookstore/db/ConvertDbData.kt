package com.jesen.pagingbookstore.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jesen.pagingbookstore.model.*
import java.util.*

/**
 * Room存储数据转换定义
 * */

class TagTypeConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data:String?):List<Tag>?{
        if (data == null){
            return null // Collections.emptyList()
        }

        val listType = object :TypeToken<List<Tag>?>(){}.type
        return gson.fromJson<List<Tag>?>(data,listType)
    }

    @TypeConverter
    fun someObjectListToString(obj:List<Tag>?):String?{
        return gson.toJson(obj)
    }
}

class UrlTypeConverter{
    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data:String?):List<Url>?{
        if (data == null){
            return null // Collections.emptyList()
        }

        val listType = object :TypeToken<List<Url>?>(){}.type
        return gson.fromJson<List<Url>?>(data,listType)
    }

    @TypeConverter
    fun someObjectListToString(obj:List<Url>?):String?{
        return gson.toJson(obj)
    }
}

class PlayInfoTypeConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data:String?):List<PlayInfo>?{
        if (data == null){
            return null // Collections.emptyList()
        }

        val listType = object :TypeToken<List<PlayInfo>?>(){}.type
        return gson.fromJson<List<PlayInfo>?>(data,listType)
    }

    @TypeConverter
    fun someObjectListToString(obj:List<PlayInfo>?):String?{
        return gson.toJson(obj)
    }
}

class StringTypeConverter{
    private val gson = Gson()

    @TypeConverter
    fun getListFromString(value: String):List<String>? {
        return value.split(",")
    }

    @TypeConverter
    fun storeListToString(list: List<String>?): String {
        if (list == null) return ""
        val str = StringBuilder(list[0])
        list.forEach {
            str.append(",").append(it)
        }
        return str.toString()
    }
}

class VideoInfoConverter {
    @TypeConverter
    fun stringToVideoInfo(value: String?):VideoInfo?{
        val type = object :TypeToken<VideoInfo?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun videoInfoToString(videoInfo:VideoInfo?): String? {
        val gson = Gson()
        return gson.toJson(videoInfo)
    }
}

class AuthorConverter {
    @TypeConverter
    fun stringToAuthor(value: String?): Author? {
        val type = object :TypeToken<Author?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun authorToString(author:Author?): String? {
        val gson = Gson()
        return gson.toJson(author)
    }
}

class ConsumptionConverter {
    @TypeConverter
    fun stringToConsumption(value: String?): Consumption? {
        val type = object :TypeToken<Consumption?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun consumptionToString(consumption:Consumption?): String? {
        val gson = Gson()
        return gson.toJson(consumption)
    }
}

class CoverConverter {
    @TypeConverter
    fun stringToCover(value: String?): Cover? {
        val type = object :TypeToken<Cover?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun coverToString(cover: Cover?): String? {
        val gson = Gson()
        return gson.toJson(cover)
    }
}

class ProviderConverter {
    @TypeConverter
    fun stringToProvider(value: String?): Provider? {
        val type = object :TypeToken<Provider?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun providerToString(provider: Provider?): String? {
        val gson = Gson()
        return gson.toJson(provider)
    }
}

class WebUrlConverter {
    @TypeConverter
    fun stringToWebUrl(value: String?): WebUrl? {
        val type = object :TypeToken<WebUrl?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun webUrlToString(webUrl: WebUrl?): String? {
        val gson = Gson()
        return gson.toJson(webUrl)
    }
}

class ShieldConverter {
    @TypeConverter
    fun stringToUrl(value: String?): Shield? {
        val type = object :TypeToken<Shield?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun urlToString(shield: Shield?): String? {
        val gson = Gson()
        return gson.toJson(shield)
    }
}

class FollowConverter {
    @TypeConverter
    fun stringToUrl(value: String?): Follow? {
        val type = object :TypeToken<Follow?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun urlToString(follow: Follow?): String? {
        val gson = Gson()
        return gson.toJson(follow)
    }
}


class UrlConverter {
    @TypeConverter
    fun stringToUrl(value: String): Url? {
        val type = object :TypeToken<Url?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun urlToString(url:Url?): String {
        val gson = Gson()
        return gson.toJson(url)
    }
}


class VideoItemTypeConverter{

    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data:String?):List<VideoItem>?{
        if (data == null){
            return null // Collections.emptyList()
        }

        val listType = object :TypeToken<List<VideoItem>?>(){}.type
        return gson.fromJson<List<VideoItem>?>(data,listType)
    }

    @TypeConverter
    fun someObjectListToString(obj:List<VideoItem>?):String?{
        return gson.toJson(obj)
    }
}


class VideoItemConverter {
    @TypeConverter
    fun stringToVideoItem(value: String): Url? {
        val type = object :TypeToken<VideoItem?>(){

        }.type
        return Gson().fromJson(value,type)
    }
    @TypeConverter
    fun videoItemToString(videoItem:VideoItem?): String {
        val gson = Gson()
        return gson.toJson(videoItem)
    }
}