package com.timepad.timepadtracker.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timepad.timepadtracker.databinding.ItemTasksBinding
import com.timepad.timepadtracker.domain.Task

class TasksAdapter : ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksDiffUtilCallback()) {

    inner class TasksViewHolder(val binding: ItemTasksBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Task) {
            binding.ivTaskIcon.setImageResource(item.iconId)
            binding.tvTaskTitle.text = item.name
            binding.tvTimeSpent.text = item.totalTimeInMillis.toString()
            binding.tvFirstCategory.text = "Personal"
            binding.tvSecondCategory.text = "Sport"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            ItemTasksBinding.inflate(
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