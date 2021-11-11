package com.jesen.flowcoroutine.sharedflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SharedViewModel:ViewModel() {

    private lateinit var job:Job

    fun startRefresh(){
        job = viewModelScope.launch (Dispatchers.IO){
            while (true){
                LocalEventBs.postEvent(Event(System.currentTimeMillis()))
            }
        }
    }

    fun stopRefresh(){
        job.cancel()
    }
}