package com.jesen.jetpackpaging.net

import com.jesen.jetpackpaging.model.ExamQuestions
import com.jesen.jetpackpaging.net.RetrofitClient.APP_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ExamQuestionApi {

    @GET("jisuapi/driverexamQuery")
    suspend fun getQuestionList(
        @Query("pagenum") pagenum: Int,
        @Query("pagesize") pagesize: Int = 10,
        @Query("type") type: String = "C1",
        @Query("subject") subject: Int = 1,
        @Query("sort") sort: String = "normal",
        @Query("appkey") appkey: String = APP_KEY,
    ): ExamQuestions
}