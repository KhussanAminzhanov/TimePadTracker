package com.timepad.timepadtracker.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timepad.timepadtracker.databinding.ItemTasksRvBinding
import com.timepad.timepadtracker.domain.Task

class TasksAdapter : ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksDiffUtilCallback()) {

    inner class TasksViewHolder(val binding: ItemTasksRvBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.ivTaskIcon.setImageResource(item.iconId)
            binding.tvTaskTitle.text = item.name
            binding.tvTimeSpent.text = item.totalTimeInMillis.toString()
            binding.tvFirstCategory.text = item.tags[0]
            binding.tvSecondCategory.text = item.tags[1]
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            ItemTasksRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class TasksDiffUtilCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}