package com.timepad.timepadtracker.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.databinding.FragmentHomeBinding
import com.timepad.timepadtracker.domain.Task

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val oneItem = Task(tags = listOf("Work", "Personal"))

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

        val adapter = TasksAdapter()
        binding.rvTasks.adapter = adapter
        adapter.submitList(listOf(oneItem, oneItem))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}