package com.timepad.timepadtracker.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.timepad.timepadtracker.databinding.ItemTaskBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.utils.formatTimeMillis

class TasksAdapter(
    private val onClick: (Task) -> Unit
) : ListAdapter<Task, TasksAdapter.TasksViewHolder>(TasksDiffUtilCallback()) {

    inner class TasksViewHolder(private val binding: ItemTaskBinding, val onClick: (Task) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(task: Task) {
            binding.ivTaskIcon.setImageResource(task.iconId)
            binding.tvTaskTitle.text = task.name
            binding.tvTimeSpent.text = task.totalTimeInMillis.formatTimeMillis("%02d:%02d:%02d")
            binding.tvCategory.text = task.category
            binding.btnOpenTimer.setOnClickListener { onClick(task) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        return holder.bind(getItem(position))
    }
}

class TasksDiffUtilCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
}