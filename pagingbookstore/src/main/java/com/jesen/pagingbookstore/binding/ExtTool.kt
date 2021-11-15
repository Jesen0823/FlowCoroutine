package com.jesen.pagingbookstore.binding

import android.util.Log
import android.view.View

fun parsePathRegex() {
    val test = "/Users/jesen/kotlin/readme.md";
    val regex = """(.+)/(.+)\.(.+)""".toRegex()
    val matchResult = regex.matchEntire(test)
    if (matchResult != null) {
        val (directory, fileName, extension) = matchResult.destructured
        Log.e("Test", "directory : $directory ,fileName : $fileName, extension : $extension")
    }
}

/**
 * String扩展函数 字符串截取 获取start的值
 * http://baobab.kaiyanapp.com/api/v4/discovery/hot?start=19&num=6
 * */
fun String.parseNextPageStart(): Int {
    val reg = """\b([\d]+)""".toRegex()
    return reg.findAll(this).elementAt(0).value.toInt()
}

/**
 * 自定义View扩展属性
 * */
inline var View.isVisible: Boolean
    get() = visibility == View.VISIBLE
    set(value) {
        visibility = if (value) View.VISIBLE else View.GONE
    }