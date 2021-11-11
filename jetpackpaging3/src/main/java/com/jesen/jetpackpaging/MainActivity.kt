package com.jesen.jetpackpaging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.jesen.jetpackpaging.databinding.ActivityMainBinding
import com.jesen.jetpackpaging.paging.ExamPagingAdapter
import com.jesen.jetpackpaging.paging.LoadMoreAdapter
import com.jesen.jetpackpaging.vm.ExamViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val mBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<ExamViewModel>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)

        val adapter = ExamPagingAdapter(this)

        mBinding.apply {
            // 适配加载更多动效
            recyclerView.adapter = adapter.withLoadStateFooter(LoadMoreAdapter(this@MainActivity))
            recyclerView.addItemDecoration(DividerItemDecoration(
                this@MainActivity,
                DividerItemDecoration.VERTICAL,
            ))
        }

        lifecycleScope.launchWhenCreated {
            viewModel.loadExam().collectLatest {
                adapter.submitData(it)
            }
        }
    }
}