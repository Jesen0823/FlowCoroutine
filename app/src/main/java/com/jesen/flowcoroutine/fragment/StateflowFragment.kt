package com.jesen.flowcoroutine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jesen.flowcoroutine.R
import com.jesen.flowcoroutine.databinding.FragmentStateflowBinding
import com.jesen.flowcoroutine.stateflow.NumViewModel
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass.
 * Use the [StateflowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StateflowFragment : Fragment() {

    private val mBinding by lazy { FragmentStateflowBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<NumViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_stateflow, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            btnMinus.setOnClickListener { viewModel.decrement() }
            btnPlus.setOnClickListener { viewModel.increment() }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.number.collect { value ->
                mBinding.tvResult.text = "$value"
            }
        }
    }
}