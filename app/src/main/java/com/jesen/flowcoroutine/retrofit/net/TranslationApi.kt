package com.jesen.flowcoroutine.retrofit.net

import com.jesen.flowcoroutine.retrofit.model.Translation
import retrofit2.http.GET
import retrofit2.http.Query

// API: http://dict.youdao.com/suggest?q=love&num=1&doctype=json
// from: https://www.free-api.com/doc/522

interface TranslationApi {

    @GET("suggest")
    suspend fun translateInput(
        @Query("q") key: String,
        @Query("num") num: Int = 10,
        @Query("doctype") docType: String = "json"
    ): Translation
}