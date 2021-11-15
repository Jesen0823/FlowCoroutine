package com.jesen.pagingbookstore.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jesen.pagingbookstore.model.VideoItem
import com.jesen.pagingbookstore.repository.Repository

class MainViewModel @ViewModelInject constructor(
    videoRepository: Repository
) : ViewModel() {

    val data: LiveData<PagingData<VideoItem>> =
        videoRepository.fetchVideoList().cachedIn(viewModelScope)
            .asLiveData()
}