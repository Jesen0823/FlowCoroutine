package com.jesen.flowcoroutine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.jesen.flowcoroutine.R
import com.jesen.flowcoroutine.databinding.FragmentSharedFlowBinding
import com.jesen.flowcoroutine.sharedflow.SharedViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [SharedFlowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SharedFlowFragment : Fragment() {
    private val mBinding by lazy { FragmentSharedFlowBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<SharedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_shared_flow, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            btnStart.setOnClickListener { viewModel.startRefresh() }
            btnStop.setOnClickListener { viewModel.stopRefresh() }
        }
    }
}