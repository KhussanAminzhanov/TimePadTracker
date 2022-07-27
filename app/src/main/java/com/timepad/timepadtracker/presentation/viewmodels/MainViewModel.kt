package com.timepad.timepadtracker.presentation.viewmodels

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.framework.Interactions
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
    val categoriesOfTasks = listOf("Work", "Personal", "Sport", "Hobby", "Leisure Time", "Other")

    val tasksWithIcon =
        mapOf(
            categoriesOfTasks[0] to R.drawable.icon_monitor_circle,
            categoriesOfTasks[1] to R.drawable.icon_book_circle,
            categoriesOfTasks[2] to R.drawable.icon_barbell_circle,
            categoriesOfTasks[3] to R.drawable.icon_book_circle,
            categoriesOfTasks[4] to R.drawable.icon_code_circle
        )

    private val selectedTask = MutableLiveData<Task>()

    private lateinit var countDownTimer: CountDownTimer
    private var oneSessionTime: Long = 25 * ONE_MINUTE

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
                selectedTask.value?.addToTotalTime(oneSessionTime)
                selectedTask.value?.let { updateTask(it) }
            }
        }
        countDownTimer.start()
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        _timerIsRunning.value = TimerState.PAUSED
    }

    fun setSelectedTask(task: Task) {
        selectedTask.value = task
        oneSessionTime = task.oneSessionTime
        _timeLeftInMillis.value = oneSessionTime
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

    companion object {
        const val ONE_MINUTE: Long = 60000
        const val COUNTDOWN_INTERVAL: Long = 1000
    }
}