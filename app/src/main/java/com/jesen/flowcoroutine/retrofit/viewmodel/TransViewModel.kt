package com.jesen.flowcoroutine.retrofit.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jesen.flowcoroutine.retrofit.model.Translation
import com.jesen.flowcoroutine.retrofit.net.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class TransViewModel(app: Application) : AndroidViewModel(app) {

    // LiveData为了防止Fragment中searchArticle里面flow嵌套
     val translation = MutableLiveData<Translation>()

    /*fun searchArticle(key: String) = flow {
        val result = RetrofitClient.translationApi.translateInput(key)
        emit(result)
    }.flowOn(Dispatchers.IO)
        .catch { e -> e.printStackTrace() }*/

    fun translatingInput(key: String) {
        viewModelScope.launch {
            flow {
                val result = RetrofitClient.translationApi.translateInput(key)
                Log.d("ViewModel--","result: ${result.result.msg}")
                emit(result)
            }.flowOn(Dispatchers.IO)
                .catch { e -> e.printStackTrace() }
                .collect {
                    translation.setValue(it)
                }
        }
    }


}