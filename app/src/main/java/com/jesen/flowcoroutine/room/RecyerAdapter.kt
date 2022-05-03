package com.jesen.flowcoroutine.room

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jesen.flowcoroutine.databinding.RoomDataItemBinding
import com.jesen.flowcoroutine.room.bean.User

class RecyerAdapter(private val context: Context): RecyclerView.Adapter<RecyerAdapter.MyViewHolder>() {

    private var mData = ArrayList<User>()


    fun setData(data:List<User>){
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }

    class MyViewHolder(val bindingView:ViewBinding): RecyclerView.ViewHolder(bindingView.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RoomDataItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {
        val item = mData[position]

        val binding = holderMy.bindingView as RoomDataItemBinding
        binding.textView.text = "${item.uid}, ${item.firstName}, ${item.lastName}"
    }

    override fun getItemCount(): Int  = mData.size
}