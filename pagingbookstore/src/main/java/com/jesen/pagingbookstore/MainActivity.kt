package com.jesen.pagingbookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.jesen.pagingbookstore.adapter.FooterAdapter
import com.jesen.pagingbookstore.adapter.VideoListAdapter
import com.jesen.pagingbookstore.databinding.ActivityMainBinding
import com.jesen.pagingbookstore.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by viewModels<MainViewModel>()
    private val mAdapter by lazy { VideoListAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        viewModel.data.observe(this, {
            mAdapter.submitData(lifecycle, it)
        })

        // 下拉加载更多 withLoadStateFooter
        mBinding.recyclerView.adapter = mAdapter.withLoadStateFooter(FooterAdapter(mAdapter,this))
    }
}