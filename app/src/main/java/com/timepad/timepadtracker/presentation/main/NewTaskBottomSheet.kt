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
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.LocalDate

class NewTaskBottomSheet : BottomSheetDialogFragment() {

    private val mainViewModel: MainViewModel by sharedViewModel()
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
                mainViewModel.categoriesOfTasks
            )
        )

        binding.btnCancel.setOnClickListener { dismiss() }

        binding.btnAddTask.setOnClickListener {
            if (!checkInputData()) return@setOnClickListener
            val name = binding.edtTaskName.text.toString()
            val category = binding.tvTaskCategory.text.toString()
            val date = LocalDate.now().toEpochDay()
            val iconId = mainViewModel.tasksWithIcon[category] ?: R.drawable.icon_code_circle
            val newTask = Task(
                iconId = iconId,
                daySinceEpoch = date,
                name = name,
                category = category,
            )
            mainViewModel.addTask(newTask)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkInputData(): Boolean {
        if (binding.edtTaskName.text.isNullOrBlank()) return false
        if (binding.tvTaskCategory.text.isNullOrBlank()) return false
        return true
    }
}