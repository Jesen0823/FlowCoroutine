package com.jesen.jetpackpaging.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jesen.jetpackpaging.model.Question
import com.jesen.jetpackpaging.paging.ExamPagingSource
import kotlinx.coroutines.flow.Flow

class ExamViewModel : ViewModel() {

    // 为了把请求结果保存在ViewModel,不发生数据丢失，以属性存储下来
    private val result by lazy {
        Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 10, // 第一次加载数量，如果不设置的话是 pageSize * 3
                prefetchDistance = 2,
            ),
            pagingSourceFactory = { ExamPagingSource() }
        // 缓存数据到ViewModel
        ).flow.cachedIn(viewModelScope)
    }

    fun loadExam(): Flow<PagingData<Question>> {
        return result
    }
}