package com.jesen.pagingbookstore.init

import android.content.Context
import androidx.startup.Initializer

/**
 * Startup 是Jetpack新成员，提供了App启动时初始化组件的方法
 * */
class AppInitializer:Initializer<Unit> {
    // 初始化APP
    override fun create(context: Context) {
        AppHelper.init(context)
    }


    // 初始化各个library
    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }
}
