package com.jesen.jetpackpaging.paging

import androidx.paging.PagingSource
import com.jesen.jetpackpaging.model.Question
import com.jesen.jetpackpaging.net.ExamQuestionApi
import com.jesen.jetpackpaging.net.RetrofitClient

class ExamPagingSource : PagingSource<Int, Question>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Question> {

        val currentPage = params.key ?: 1
        val pageSize = params.loadSize
        val response = RetrofitClient.ctreateApi(ExamQuestionApi::class.java)
            .getQuestionList(currentPage, pageSize)

        val responseList = mutableListOf<Question>()
        val data = response?.result?.resultData?.QuestionList ?: emptyList<Question>()
        responseList.addAll(data)

        // 加载分页
        val everyPageSize = 5
        val initPageSize = 10
        val preKey = if (currentPage == 1) null else currentPage.minus(1)
        val nextKey = if (currentPage == 1) {
            initPageSize / everyPageSize + 1
        } else {
            currentPage.plus(1)
        }

        return try {
            LoadResult.Page(
                data = responseList,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}