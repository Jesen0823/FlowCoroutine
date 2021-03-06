package com.jesen.flowcoroutine.sharedflow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.jesen.flowcoroutine.R
import com.jesen.flowcoroutine.databinding.FragmentTextBinding
import kotlinx.coroutines.flow.collect

/**
 * A simple [Fragment] subclass.
 * Use the [TextFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TextFragment : Fragment() {
    private val mBinding by lazy { FragmentTextBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_text, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenCreated {
            LocalEventBs.events.collect { event ->
                mBinding.timeTv.text = event.timestamp.toString()
            }
        }
    }
}