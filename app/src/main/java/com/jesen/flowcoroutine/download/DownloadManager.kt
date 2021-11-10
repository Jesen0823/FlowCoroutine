package com.jesen.flowcoroutine.download

import com.jesen.flowcoroutine.util.copyToo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.IOException

object DownloadManager {
    fun download(url: String, file: File): Flow<DownloadStatus> {
        return flow {
            val request = Request.Builder().url(url).get().build()
            val response = OkHttpClient.Builder().build().newCall(request).execute()
            if (response.isSuccessful) {
                //body如果为null，继续执行，故意抛出异常
                response.body()!!.let { body ->
                    val total = body.contentLength()
                    file.outputStream().use { output ->
                        val input = body.byteStream()
                        var emittedProgress = 0L
                        input.copyToo(output) { bytesCopied ->
                            val progress = bytesCopied * 100 / total
                            //不用每次都发射进度，降低频率
                            if (progress - emittedProgress > 5) {
                                delay(100)
                                emit(DownloadStatus.Progress(progress.toInt()))
                                emittedProgress = progress
                            }
                        }
                    }
                    emit(DownloadStatus.Done(file))
                }
            } else {
                throw IOException(response.toString())
            }
        }.catch {
            file.delete()
            emit(DownloadStatus.Error(it))
        }.flowOn(Dispatchers.IO)
    }
}