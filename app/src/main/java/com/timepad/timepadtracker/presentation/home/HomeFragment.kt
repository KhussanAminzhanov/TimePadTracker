package com.timepad.timepadtracker.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentHomeBinding
import com.timepad.timepadtracker.utils.findTopNavController
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by sharedViewModel()

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

        adapter = TasksAdapter()
        binding.rvTasks.adapter = adapter

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.frameLayout.setOnClickListener {
            findTopNavController().navigate(R.id.timerFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}