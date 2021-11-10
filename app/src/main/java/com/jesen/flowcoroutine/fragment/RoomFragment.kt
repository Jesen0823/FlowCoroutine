package com.jesen.flowcoroutine.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.jesen.flowcoroutine.R
import com.jesen.flowcoroutine.databinding.FragmentRoomBinding
import com.jesen.flowcoroutine.room.RecyerAdapter
import com.jesen.flowcoroutine.room.RoomViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [RoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomFragment : Fragment() {

    private val mBinding by lazy{
        FragmentRoomBinding.inflate(layoutInflater)
    }

    private val viewModel by  viewModels<RoomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_room, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.apply {
            btnAddUser.setOnClickListener {
                viewModel.insert(
                    etUserId.text.toString(),
                    etFirstName.text.toString(),
                    etLastName.text.toString()
                )
            }
        }

        context?.let {
            val adapter = RecyerAdapter(it)
            mBinding.recyclerView.adapter = adapter
            mBinding.recyclerView.addItemDecoration(DividerItemDecoration(it,DividerItemDecoration.VERTICAL))
            lifecycleScope.launch {
                viewModel.getAll().collect { userList ->
                    adapter.setData(userList)
                }
            }
        }
    }

}