package com.jesen.pagingbookstore.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.jesen.pagingbookstore.binding.isVisible
import com.jesen.pagingbookstore.databinding.FooterLoadStateLayoutBinding

class FooterAdapter(
    private val adapter: VideoListAdapter,
    private val context: Context
) : LoadStateAdapter<FooterStateItemViewHolder>() {
    override fun onBindViewHolder(holder: FooterStateItemViewHolder, loadState: LoadState) {
        // 水平居中
        val params = holder.itemView.layoutParams
        if(params is StaggeredGridLayoutManager.LayoutParams) params.isFullSpan = true

        holder.bindData(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterStateItemViewHolder {
        val binding =
            FooterLoadStateLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return FooterStateItemViewHolder(binding) { adapter.retry() }
    }
}

class FooterStateItemViewHolder(
    private val binding: FooterLoadStateLayoutBinding,
    private val retryCallback: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    // 根据加载状态处理UI
    fun bindData(loadState: LoadState) {
        binding.apply {
            // 正在加载则展示loading
            progress.isVisible = loadState is LoadState.Loading
            // 加载失败展示重试按钮
            retryBtn.isVisible = loadState is LoadState.Error
            retryBtn.setOnClickListener { retryCallback }
            // 加载失败显示错误信息
            errorLoad.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
            errorLoad.text = (loadState as? LoadState.Error)?.error?.message
        }
    }
}