package com.timepad.timepadtracker.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentHomeBinding
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.adapters.TasksAdapter
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.findTopNavController
import com.timepad.timepadtracker.utils.formatTimeMillis
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val mainViewModel: MainViewModel by sharedViewModel()

    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        setupListeners()
        setupObservers()
    }

    private fun setupRecyclerview() {
        adapter = TasksAdapter(::onTaskItemClick)
        binding.rvTasks.adapter = adapter
    }

    private fun setupListeners() {
        binding.frameLayout.setOnClickListener {
            findTopNavController().navigate(R.id.timerFragment)
        }
        binding.tvSeeAll.setOnClickListener {
            findTopNavController().navigate(R.id.historyFragment)
        }
    }

    private fun setupObservers() {
        mainViewModel.tasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        mainViewModel.timeLeftInMillis.observe(viewLifecycleOwner) {
            binding.tvTimerHome.text = it.formatTimeMillis("%02d:%02d:%02d")
        }
    }

    private fun onTaskItemClick(task: Task) {
        findTopNavController().navigate(R.id.timerFragment)
        mainViewModel.setSelectedTask(task)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}