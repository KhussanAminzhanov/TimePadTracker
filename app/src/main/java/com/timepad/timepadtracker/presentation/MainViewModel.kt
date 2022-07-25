package com.timepad.timepadtracker.presentation

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    enum class TimerState {
        RUNNING, PAUSED, STOPPED
    }

    private lateinit var countDownTimer: CountDownTimer

    private var oneSessionTime: Long = 1 * ONE_MINUTE

    private val _timeLeftInMillis = MutableLiveData(oneSessionTime)
    val timeLeftInMillis: LiveData<Long> = _timeLeftInMillis

    private val _timerIsRunning = MutableLiveData(TimerState.STOPPED)
    val timerIsRunning: LiveData<TimerState> = _timerIsRunning

    fun startOrPauseTimer() {
        if (_timerIsRunning.value != TimerState.RUNNING) {
            startTimer()
        } else {
            pauseTimer()
        }
    }

    fun stopTimer() {
        if (_timerIsRunning.value != TimerState.STOPPED) {
            countDownTimer.cancel()
            _timerIsRunning.value = TimerState.STOPPED
            _timeLeftInMillis.value = oneSessionTime
        }
    }

    private fun startTimer() {
        _timerIsRunning.value = TimerState.RUNNING
        countDownTimer = object : CountDownTimer(
            _timeLeftInMillis.value ?: oneSessionTime, COUNTDOWN_INTERVAL
        ) {
            override fun onTick(millisUntilFinish: Long) {
                _timeLeftInMillis.value = millisUntilFinish
            }

            override fun onFinish() {
                _timerIsRunning.value = TimerState.STOPPED
                _timeLeftInMillis.value = oneSessionTime
            }
        }
        countDownTimer.start()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        _timerIsRunning.value = TimerState.PAUSED
    }

    companion object {
        const val ONE_MINUTE: Long = 60000
        const val COUNTDOWN_INTERVAL: Long = 1000
    }
}