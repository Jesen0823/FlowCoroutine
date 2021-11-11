package com.jesen.jetpackpaging.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jesen.jetpackpaging.databinding.ItemExamLayoutBinding
import com.jesen.jetpackpaging.databinding.ItemExamLayoutChoiceBinding
import com.jesen.jetpackpaging.model.Question

class ExamPagingAdapter(private val context: Context) :
    PagingDataAdapter<Question, BindingViewHolder>(DiffUtilCallback()) {

    companion object {
        private const val TYPE_JUDGMENT = 0
        private const val TYPE_CHOICE = 1
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        val question = getItem(position)
        val itemType = getItemViewType(position)

        question?.let {
            if (itemType == TYPE_JUDGMENT) {
                val judgeBinding = holder.binding as ItemExamLayoutBinding
                judgeBinding.question = it
            } else {
                val choiceBinding = holder.binding as ItemExamLayoutChoiceBinding
                choiceBinding.question = it
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val judgeBinding =
            ItemExamLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        val choiceBinding =
            ItemExamLayoutChoiceBinding.inflate(LayoutInflater.from(context), parent, false)
        return BindingViewHolder(if (viewType == TYPE_JUDGMENT) judgeBinding else choiceBinding)
    }

    override fun getItemViewType(position: Int): Int {
        val question = getItem(position)
        val isEmpty = (question?.option1.isNullOrBlank() && question?.option2.isNullOrBlank())
        return if (isEmpty) TYPE_JUDGMENT else TYPE_CHOICE
    }
}

class BindingViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

/**
 * 差分，如果两个条目一致就不会重绘
 * */
class DiffUtilCallback : DiffUtil.ItemCallback<Question>() {
    override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean {
        return oldItem.question == newItem.question
    }

    override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean =
        oldItem.question == newItem.question
}