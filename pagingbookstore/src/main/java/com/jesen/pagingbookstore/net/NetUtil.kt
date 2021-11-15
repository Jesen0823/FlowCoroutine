package com.jesen.pagingbookstore.net

import android.net.ConnectivityManager
import android.net.LinkProperties
import android.net.Network
import android.net.NetworkCapabilities
import android.util.Log

enum class LinkType{WIFI, MOBILE,OTHER,UNKNOW}

class NetUtil : ConnectivityManager.NetworkCallback() {

    companion object {
        // 是否有网络连接
        var isHasNetLinked: Boolean = false
        // 连接是否可用
        var isLinkedNetActive: Boolean = false
        var linkNetType = LinkType.UNKNOW
    }

    //网络连接成功
    override fun onAvailable(network: Network) {
        Log.d("Net===", "网络连接成功")
        super.onAvailable(network)
    }

    //网络已断开连接
    override fun onLost(network: Network) {
        Log.d("Net===", "网络已断开连接")
        super.onLost(network)
    }

    override fun onLosing(network: Network, maxMsToLive: Int) {
        Log.d("Net===", "网络正在断开连接")
        super.onLosing(network, maxMsToLive)
    }

    //无网络
    override fun onUnavailable() {
        Log.d("Net===", "网络连接超时或者网络连接不可达")
        super.onUnavailable()
    }

    //当网络状态修改（网络依然可用）时调用
    override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
        super.onCapabilitiesChanged(network, networkCapabilities)
        Log.d("Net===", "net status change! 网络连接改变")
        isHasNetLinked =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        isLinkedNetActive =
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
        if (isLinkedNetActive){
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.d("Net===","当前在使用WiFi上网")
                linkNetType = LinkType.WIFI
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.d("Net===","当前在使用数据网络上网")
                linkNetType = LinkType.MOBILE
            } else{
                Log.d("Net===","当前在使用其他网络")
                linkNetType = LinkType.OTHER
                // 未知网络，包括蓝牙、VPN等
            }
        }
    }

    //当访问的网络被阻塞或者解除阻塞时调用
    override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
        super.onBlockedStatusChanged(network, blocked)
    }

    //当网络连接属性发生变化时调用
    override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
        super.onLinkPropertiesChanged(network, linkProperties)
    }
}