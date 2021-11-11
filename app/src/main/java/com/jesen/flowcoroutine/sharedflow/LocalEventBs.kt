package com.jesen.flowcoroutine.sharedflow

import kotlinx.coroutines.flow.MutableSharedFlow

object LocalEventBs {
    val events = MutableSharedFlow<Event>()

    suspend fun postEvent(event:Event){
        events.emit(event)
    }
}

data class Event(val timestamp:Long)