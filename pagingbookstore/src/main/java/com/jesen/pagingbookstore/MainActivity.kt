package com.jesen.pagingbookstore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.jesen.pagingbookstore.adapter.FooterAdapter
import com.jesen.pagingbookstore.adapter.VideoListAdapter
import com.jesen.pagingbookstore.databinding.ActivityMainBinding
import com.jesen.pagingbookstore.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

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
            mBinding.swipeRefresh.isEnabled = true
        })

        // 上拉加载更多动效 withLoadStateFooter
        mBinding.recyclerView.adapter = mAdapter.withLoadStateFooter(FooterAdapter(mAdapter,this))

        // 下拉刷新动效
        mBinding.swipeRefresh.setOnRefreshListener {
            mAdapter.refresh()
        }
        lifecycleScope.launchWhenCreated {
            mAdapter.loadStateFlow.collectLatest { state->
                mBinding.swipeRefresh.isRefreshing = state.refresh is LoadState.Loading
            }
        }
    }
}