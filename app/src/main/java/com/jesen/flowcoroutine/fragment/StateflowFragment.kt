package com.jesen.flowcoroutine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jesen.flowcoroutine.R
import com.jesen.flowcoroutine.databinding.FragmentStateflowBinding

/**
 * A simple [Fragment] subclass.
 * Use the [StateflowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StateflowFragment : Fragment() {

    val mBinding by lazy { FragmentStateflowBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_stateflow, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}