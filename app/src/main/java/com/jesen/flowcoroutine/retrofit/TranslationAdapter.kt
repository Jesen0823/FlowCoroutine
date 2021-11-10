package com.jesen.flowcoroutine.retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jesen.flowcoroutine.databinding.RetrofitItemBinding
import com.jesen.flowcoroutine.retrofit.model.TransEntry

class TranslationAdapter(private val context: Context) :
    RecyclerView.Adapter<TranslationAdapter.MyViewHolder>() {

    private var mData = ArrayList<TransEntry>()


    fun setData(data: List<TransEntry>) {
        mData.clear()
        mData.addAll(data)
        notifyDataSetChanged()
    }


    class MyViewHolder(val bindingView: ViewBinding) : RecyclerView.ViewHolder(bindingView.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RetrofitItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holderMy: MyViewHolder, position: Int) {
        val item = mData[position]

        val binding = holderMy.bindingView as RetrofitItemBinding
        binding.tvContent.text = "${item.entry} |  ${item.explain}"
    }

    override fun getItemCount(): Int = mData.size
}