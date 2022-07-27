package com.timepad.timepadtracker.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.BottomSheetAddTaskBinding
import com.timepad.timepadtracker.domain.Task

import com.timepad.timepadtracker.presentation.viewmodels.TasksViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.LocalDate

class NewTaskBottomSheet : BottomSheetDialogFragment() {

    private val viewModel: TasksViewModel by sharedViewModel()
    private var _binding: BottomSheetAddTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvTaskCategory.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                viewModel.categoriesOfTasks
            )
        )

        binding.btnCancel.setOnClickListener { dismiss() }

//        fun iconGetter(value:String):Int{
//            return categories.map { it.img }[categories.map { it.name }.indexOf(value)]
//        }

        binding.btnAddTask.setOnClickListener {
            val newTask = Task(
                0,
                viewModel.tasksWithIcon.map { it.value }[viewModel.categoriesOfTasks.indexOf(binding.tvTaskCategory.text.toString())],
                binding.tvTaskName.text.toString(),
                listOf(
                    binding.tvTaskCategory.text.toString(),
                    binding.tvTaskCategory.text.toString()
                ),
                0,
                0, LocalDate.now().toEpochDay()
            )
            viewModel.addTask(newTask)
            dismiss()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}