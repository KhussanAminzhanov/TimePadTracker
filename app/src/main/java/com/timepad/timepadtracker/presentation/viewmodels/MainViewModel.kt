package com.timepad.timepadtracker.presentation.viewmodels

import android.os.CountDownTimer
import androidx.compose.animation.core.FloatTweenSpec
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.domain.TaskRecord
import com.timepad.timepadtracker.framework.Interactions
import com.timepad.timepadtracker.utils.getCurrentDaySinceEpoch
import com.timepad.timepadtracker.utils.getCurrentHourOfDay
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlin.math.floor
import kotlin.math.roundToInt


class MainViewModel(
    private val interactions: Interactions,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    enum class TimerState {
        RUNNING, PAUSED, STOPPED
    }

    val categories = listOf("Work", "Study", "Workout", "Hobby")
    val tasksWithIcon = mapOf(
        categories[0] to R.drawable.icon_monitor_circle,
        categories[1] to R.drawable.icon_book_circle,
        categories[2] to R.drawable.icon_barbell_circle,
        categories[3] to R.drawable.icon_code_circle
    )

    val tasks: LiveData<List<Task>> = interactions.getByDate(LocalDate.now().toEpochDay())

    private val selectedTask = MutableLiveData<Task>()

    private lateinit var countDownTimer: CountDownTimer
    private var oneSessionTime: Long = 0 * ONE_MINUTE

    private val _timeLeftInMillis = MutableLiveData(oneSessionTime)
    val timeLeftInMillis: LiveData<Long> = _timeLeftInMillis

    private val _timerIsRunning = MutableLiveData(TimerState.STOPPED)
    val timerIsRunning: LiveData<TimerState> = _timerIsRunning

    fun startOrPauseTimer() {
        if (selectedTask.value == null) return
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
                _timeLeftInMillis.value = 0
                onTimerFinish()
            }
        }
        countDownTimer.start()
    }

    private fun onTimerFinish() {
        val selectedTask = selectedTask.value ?: return
        selectedTask.totalTimeInMillis += oneSessionTime
        updateTask(selectedTask)

        val taskRecord = TaskRecord(
            epochDay = getCurrentDaySinceEpoch(),
            hour = getCurrentHourOfDay(),
            duration = selectedTask.duration
        )
        addTaskRecord(taskRecord)
    }

    private fun pauseTimer() {
        countDownTimer.cancel()
        _timerIsRunning.value = TimerState.PAUSED
    }

    fun setSelectedTask(task: Task) {
        if (timerIsRunning.value == TimerState.RUNNING) return
        selectedTask.value = task
        oneSessionTime = task.duration
        _timeLeftInMillis.value = task.duration
    }

    fun getSelectedTaskCategory(): String = selectedTask.value?.category ?: "None"
    fun getSelectedTaskTitle(): String = selectedTask.value?.name ?: "Undefined"

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