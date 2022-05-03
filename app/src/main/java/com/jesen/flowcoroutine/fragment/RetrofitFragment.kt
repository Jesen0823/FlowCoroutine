package com.jesen.flowcoroutine.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.jesen.flowcoroutine.databinding.FragmentRetrofitBinding
import com.jesen.flowcoroutine.retrofit.TranslationAdapter
import com.jesen.flowcoroutine.retrofit.viewmodel.TransViewModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass.
 * Use the [RetrofitFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RetrofitFragment : Fragment() {
    private val mBinding by lazy { FragmentRetrofitBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<TransViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_retrofit, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            mBinding.etSearch.textWatcherFlow().collect {
                Log.d("RetrofitFragment--", "collect key: $it")

                // 发生了flow嵌套
                /*viewModel.translatingInput(it).collect {}*/
                if (it.isNotBlank()) {
                    viewModel.translatingInput(it)
                }
            }
        }

        context?.let {
            val adapter = TranslationAdapter(it)
            mBinding.recyclerView.adapter = adapter
            mBinding.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    it,
                    DividerItemDecoration.VERTICAL
                )
            )

            viewModel.translation.observe(viewLifecycleOwner) { transResult ->
                adapter.setData(transResult.data.entries)
            }
        }
    }

    // 监听输入 定义扩展函数
    private fun TextView.textWatcherFlow(): Flow<String> = callbackFlow {
        // 为了将afterTextChanged中的数据变成Flow
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                // 将数据变化给到flow,即callbackFlow返回的Flow
                offer(p0.toString())
            }
        }
        addTextChangedListener(textWatcher)
        awaitClose { removeTextChangedListener(textWatcher) }
    }

}