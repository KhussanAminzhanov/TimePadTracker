package com.timepad.timepadtracker.presentation.timer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.databinding.FragmentTimerBinding
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.formatTimeMillis
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class TimerFragment : Fragment() {

    private var _binding: FragmentTimerBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupLayout()
        setupListeners()
        setupObservers()
    }

    private fun setupLayout() {
        binding.tvTaskTitle.text = mainViewModel.getSelectedTaskTitle()
        binding.tvTaskCategory.text = mainViewModel.getSelectedTaskCategory()
    }

    private fun setupListeners() {
        binding.ibtnBack.setOnClickListener { findNavController().popBackStack() }
        binding.ibtnStartPause.setOnClickListener { mainViewModel.startOrPauseTimer() }
        binding.ibtnStop.setOnClickListener { mainViewModel.stopTimer() }
    }

    private fun setupObservers() {
        mainViewModel.timeLeftInMillis.observe(viewLifecycleOwner) {
            binding.tvTimer.text = it.formatTimeMillis("%02d:%02d:%02d")
        }
        mainViewModel.timerIsRunning.observe(viewLifecycleOwner) {
            when (it) {
                MainViewModel.TimerState.STOPPED -> {
                    binding.ibtnStartPause.setImageResource(R.drawable.ic_play)
                    binding.tvStartPause.text = "Start"
                    binding.ibtnStop.visibility = View.GONE
                    binding.tvStop.visibility = View.GONE
                }
                MainViewModel.TimerState.PAUSED -> {
                    binding.ibtnStartPause.setImageResource(R.drawable.ic_play)
                    binding.tvStartPause.text = "Start"
                }
                MainViewModel.TimerState.RUNNING -> {
                    binding.ibtnStartPause.setImageResource(R.drawable.ic_pause)
                    binding.tvStartPause.text = "Pause"
                    binding.ibtnStop.visibility = View.VISIBLE
                    binding.tvStop.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}