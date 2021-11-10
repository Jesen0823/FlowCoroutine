package com.jesen.flowcoroutine.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.jesen.flowcoroutine.R
import com.jesen.flowcoroutine.databinding.FragmentDownloadBinding
import com.jesen.flowcoroutine.databinding.FragmentHomeBinding
import com.jesen.flowcoroutine.download.DownloadManager
import com.jesen.flowcoroutine.download.DownloadStatus
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.io.File


/**
 * A simple [Fragment] subclass.
 * Use the [DownloadFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DownloadFragment : Fragment() {

    private val URL = "https://t1.huishahe.com/uploads/tu/bj/tp/1225/1.jpg"

    companion object {
        private const val TAG = "DownloadFragment"
    }

    private val mBinding by lazy {
        FragmentDownloadBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            context?.apply {
                val file = File(getExternalFilesDir(null)?.path, "pic.jgp")
                Log.d(TAG, "onViewCreated: " + file.absolutePath)
                DownloadManager.download(URL, file).collect { status ->
                    when (status) {
                        is DownloadStatus.Progress -> {
                            mBinding.apply {
                                progressBar.progress = status.value
                                progressTv.text = "${status.value}%"
                            }
                        }
                        is DownloadStatus.Error -> {
                            Toast.makeText(context, "下载错误", Toast.LENGTH_SHORT).show()
                        }
                        is DownloadStatus.Done -> {
                            mBinding.apply {
                                progressBar.progress = 100
                                progressTv.text = "100%\n${file.absolutePath}$"
                            }
                            Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Log.d(TAG, "onViewCreated: 下载失败")
                        }
                    }
                }
            }
        }
    }
}