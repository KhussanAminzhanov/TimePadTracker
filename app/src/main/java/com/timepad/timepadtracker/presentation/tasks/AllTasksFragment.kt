package com.timepad.timepadtracker.presentation.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentHistoryBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.adapters.TasksAdapter
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class AllTasksFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var tasksAdapter: TasksAdapter
    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupRecyclerview()
        setupObservers()
    }

    private fun setupRecyclerview() {
        tasksAdapter = TasksAdapter(::onClick)
        binding.rvTasksHistory.adapter = tasksAdapter
    }

    private fun setupObservers() {
        mainViewModel.tasks.observe(viewLifecycleOwner) {
            tasksAdapter.submitList(it)
        }
    }

    private fun onClick(task: Task) {
        findNavController().popBackStack()
        findNavController().navigate(R.id.timerFragment)
        mainViewModel.setSelectedTask(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}