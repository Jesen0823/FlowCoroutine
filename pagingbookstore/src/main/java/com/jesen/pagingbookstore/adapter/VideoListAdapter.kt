package com.jesen.pagingbookstore.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jesen.pagingbookstore.databinding.VideoItemLayoutBinding
import com.jesen.pagingbookstore.model.VideoItem

class VideoListAdapter(private val context: Context) :
    PagingDataAdapter<VideoItem, BindingViewHolder>(DiffUtilCallback()) {
    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).let { item ->
            val binding = holder.binding as VideoItemLayoutBinding
            // 将VideoItem设置给布局
            Log.d("adapter===","VideoItem id: ${item?.id}, cur postion:$position")
            binding.videoItem = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        
        val binding = VideoItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(binding)
    }
}


class BindingViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<VideoItem>() {

    override fun areItemsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: VideoItem, newItem: VideoItem): Boolean =
        oldItem == newItem

}