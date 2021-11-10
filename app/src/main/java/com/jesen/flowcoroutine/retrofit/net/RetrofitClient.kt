package com.jesen.flowcoroutine.retrofit.net

import com.jesen.flowcoroutine.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {

    private val instance: Retrofit by lazy {

        val logInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            //显示日志
            logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val okhttpClient = OkHttpClient.Builder().addInterceptor(logInterceptor)
            .connectTimeout(5, TimeUnit.SECONDS)//设置超时时间
            .retryOnConnectionFailure(true).build()

        Retrofit.Builder()
            .client(okhttpClient)
            .baseUrl("http://dict.youdao.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val translationApi: TranslationApi by lazy {
        instance.create(TranslationApi::class.java)
    }
}