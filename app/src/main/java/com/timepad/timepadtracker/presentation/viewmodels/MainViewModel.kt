package com.timepad.timepadtracker.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.domain.TaskRecord
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.presentation.theme.*
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import com.timepad.timepadtracker.utils.getCurrentHourOfDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate


class MainViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    enum class TimerState {
        RUNNING, PAUSED, STOPPED
    }

    val tasks: LiveData<List<Task>> = interactions.getByDate(LocalDate.now().toEpochDay())

    private val _selectedTask = MutableLiveData<Task?>()

    private lateinit var countDownTimer: CountDownTimer
    private var oneSessionTime: Long = 0 * ONE_MINUTE

    private val _timeLeftInMillis = MutableLiveData(oneSessionTime)
    val timeLeftInMillis: LiveData<Long> = _timeLeftInMillis

    private val _timerIsRunning = MutableLiveData(TimerState.STOPPED)
    val timerIsRunning: LiveData<TimerState> = _timerIsRunning

    fun startOrPauseTimer() {
        if (_selectedTask.value == null) return
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
            _timeLeftInMillis.value ?: oneSessionTime, 1000
        ) {
            override fun onTick(millisUntilFinish: Long) {
                _timeLeftInMillis.value = millisUntilFinish
            }

            override fun onFinish() {
                _timerIsRunning.value = TimerState.STOPPED
                onTimerFinish()
            }
        }
        countDownTimer.start()
    }

    fun onTimerFinish() {
        val timeLeft = _timeLeftInMillis.value ?: return
        val selectedTask = _selectedTask.value ?: return
        deleteTask(selectedTask)

        val taskRecord = TaskRecord(
            epochDay = 19213,
            hour = getCurrentHourOfDay(),
            duration = selectedTask.duration - timeLeft
        )

        addTaskRecord(taskRecord)
        _selectedTask.value = null
        _timeLeftInMillis.value = 0
        _timerIsRunning.value = TimerState.STOPPED
        if (::countDownTimer.isInitialized) countDownTimer.cancel()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        _timerIsRunning.value = TimerState.PAUSED
    }

    fun setSelectedTask(task: Task) {
        if (timerIsRunning.value == TimerState.RUNNING) return
        _selectedTask.value = task
        oneSessionTime = task.duration
        _timeLeftInMillis.value = task.duration
    }

    fun getSelectedTaskCategory(): String = _selectedTask.value?.category ?: "None"
    fun getSelectedTaskTitle(): String = _selectedTask.value?.name ?: "Undefined"

    fun getTimeLeftPercentage(): Float {
        var timeLeft = timeLeftInMillis.value
        if (oneSessionTime == 0L) return 1f
        if (timeLeft == null) return 1f
        if (timeLeft != oneSessionTime && timeLeft != 0L) timeLeft -= 1000
        return (timeLeft * 100 / oneSessionTime).toFloat() / 100
    }

    fun addTask(task: Task) = viewModelScope.launch(ioDispatcher) {
        interactions.addTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch(ioDispatcher) {
        interactions.deleteTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch(ioDispatcher) {
        interactions.updateTask(task)
    }

    fun addTaskRecord(taskRecord: TaskRecord) = viewModelScope.launch(ioDispatcher) {
        interactions.addTaskRecord(taskRecord)
    }

    companion object {
        const val TAG = "MainViewModel"
        const val ONE_MINUTE: Long = 60000
        const val COUNTDOWN_INTERVAL: Long = 1000
    }
}