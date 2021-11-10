package com.jesen.flowcoroutine.download

import java.io.File

sealed class DownloadStatus{

    data class Progress(val value:Int):DownloadStatus()

    data class Error(val throwable:Throwable):DownloadStatus()

    data class Done(val file:File):DownloadStatus()

    object None:DownloadStatus()

}
