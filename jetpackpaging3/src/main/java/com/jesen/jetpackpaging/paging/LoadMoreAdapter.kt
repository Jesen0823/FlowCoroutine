package com.jesen.jetpackpaging.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.jesen.jetpackpaging.databinding.LoadingMoreViewBinding

class LoadMoreAdapter(private val context: Context) : LoadStateAdapter<BindingViewHolder>() {

    override fun onBindViewHolder(holder: BindingViewHolder, loadState: LoadState) {}

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BindingViewHolder {
        val binding = LoadingMoreViewBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(binding)
    }
}