package com.jesen.pagingbookstore.binding

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

/**
 * 网络检测
 * */
fun Context.isConnectedNet(): Boolean = run {

    val mConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        val mNetworkInfo = mConnectivityManager.activeNetworkInfo
        mNetworkInfo?.isConnectedOrConnecting == true

    } else {
        val network = mConnectivityManager.activeNetwork ?: return false
        val status = mConnectivityManager.getNetworkCapabilities(network)
            ?: return false
        status.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }
}

fun Context.isWifiConnected(): Boolean = run {
    val mConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        val mWiFiNetworkInfo = mConnectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
        mWiFiNetworkInfo?.isAvailable == true
    } else {
        val network = mConnectivityManager.activeNetwork ?: return false
        val status = mConnectivityManager.getNetworkCapabilities(network)
            ?: return false
        status.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    }
}

fun Context.isMobileConnected(): Boolean = run {
    val mConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        val mMobileNetworkInfo = mConnectivityManager
            .getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
        mMobileNetworkInfo?.isAvailable == true

    } else {
        val network = mConnectivityManager.activeNetwork ?: return false
        val status = mConnectivityManager.getNetworkCapabilities(network)
            ?: return false
        status.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}