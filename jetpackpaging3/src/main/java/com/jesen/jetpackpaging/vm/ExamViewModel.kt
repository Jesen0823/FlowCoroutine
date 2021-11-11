package com.jesen.jetpackpaging.vm

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jesen.jetpackpaging.model.Question
import com.jesen.jetpackpaging.paging.ExamPagingSource
import kotlinx.coroutines.flow.Flow

class ExamViewModel : ViewModel() {

    fun loadExam(): Flow<PagingData<Question>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 10, // 第一次加载数量，如果不设置的话是 pageSize * 3
                prefetchDistance = 2,
            ),
            pagingSourceFactory = { ExamPagingSource() }
        ).flow
    }
}