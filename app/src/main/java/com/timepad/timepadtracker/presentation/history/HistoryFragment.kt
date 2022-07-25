package com.timepad.timepadtracker.presentation.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.databinding.FragmentHistoryBinding
import com.timepad.timepadtracker.presentation.home.HomeViewModel
import com.timepad.timepadtracker.presentation.home.TasksAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var tasksAdapter: TasksAdapter
    private val homeViewModel: HomeViewModel by sharedViewModel()

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
        homeViewModel.tasks.observe(viewLifecycleOwner) {
            tasksAdapter.submitList(it)
        }
    }

    private fun onClick(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}