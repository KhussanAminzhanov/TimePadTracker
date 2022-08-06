package com.timepad.timepadtracker.presentation.screens.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.screens.home.PlaceholderText
import com.timepad.timepadtracker.presentation.screens.home.TodayTasks
import com.timepad.timepadtracker.presentation.screens.report.Header
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel

@Composable
fun AllTasksScreen(
    mainViewModel: MainViewModel,
    onTaskItemClick: (Task) -> Unit,
    onBackArrowClick: () -> Unit
) {
    val tasks by mainViewModel.tasks.observeAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Header(
            titleTextRes = R.string.all_tasks,
            onBackArrowClick = onBackArrowClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )

        if (tasks.isNullOrEmpty()) {
            PlaceholderText(R.string.history_hint)
        } else {
            tasks?.let {
                TodayTasks(
                    onTaskItemClick = onTaskItemClick,
                    tasks = it,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                )
            }
        }
    }
}
