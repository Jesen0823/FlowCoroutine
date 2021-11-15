package com.jesen.pagingbookstore.repository

import androidx.paging.PagingData
import com.jesen.pagingbookstore.model.VideoItem
import com.jesen.pagingbookstore.model.VideoStore
import kotlinx.coroutines.flow.Flow

interface Repository {

    fun fetchVideoList():Flow<PagingData<VideoItem>>
}