package com.jesen.pagingbookstore.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.jesen.pagingbookstore.R

/**
 * 图片加载框架 coil封装
 * */
@BindingAdapter("bindingAvatar")
fun bindingAvatar(imageView: ImageView,url:String){
    imageView.load(url){
        crossfade(true) // 淡入淡出
        placeholder(R.drawable.ic_cache_image)
    }
}