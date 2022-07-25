package com.timepad.timepadtracker.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentHomeBinding
import com.timepad.timepadtracker.presentation.MainViewModel
import com.timepad.timepadtracker.utils.findTopNavController
import com.timepad.timepadtracker.utils.formatTimeMillis
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by sharedViewModel()
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
        adapter = TasksAdapter(::onClick)
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
        homeViewModel.tasks.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        mainViewModel.timeLeftInMillis.observe(viewLifecycleOwner) {
            binding.tvTimerHome.text = it.formatTimeMillis("%02d:%02d:%02d")
        }
    }

    private fun onClick(){
        findTopNavController().navigate(R.id.timerFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}