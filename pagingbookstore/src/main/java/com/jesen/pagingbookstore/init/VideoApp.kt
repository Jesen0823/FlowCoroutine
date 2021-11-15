package com.jesen.pagingbookstore.init

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VideoApp : Application()

object AppHelper{
    lateinit var mContext: Context

    fun init(context: Context){
        this.mContext = context
    }
}