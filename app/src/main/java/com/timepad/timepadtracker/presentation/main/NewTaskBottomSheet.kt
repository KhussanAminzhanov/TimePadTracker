package com.timepad.timepadtracker.presentation.main

import android.content.DialogInterface
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
import com.timepad.timepadtracker.utils.getColorFromAttr
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.time.LocalDate

class NewTaskBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetAddTaskBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

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
        setupLayout()

        binding.tvTaskCategory.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                mainViewModel.categoriesOfTasks
            )
        )

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

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.edtTaskName.setText("")
        binding.tvTaskCategory.setText("")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupLayout() {
        binding.btnAddTask.setTextColor(requireContext().getColorFromAttr(android.R.attr.textColorTertiaryInverse))
    }

    private fun checkInputData(): Boolean {
        if (binding.edtTaskName.text.isNullOrBlank()) return false
        if (binding.tvTaskCategory.text.isNullOrBlank()) return false
        return true
    }
}