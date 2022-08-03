package com.timepad.timepadtracker.presentation.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import com.timepad.timepadtracker.domain.Task
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.timepad.timepadtracker.presentation.screens.main.TaskItem
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel

@Composable
fun TodayTasks(mainViewModel: MainViewModel) {

    val tasks by mainViewModel.tasks.observeAsState()

    tasks?.let {
        TodayTasksContent(tasks = it)
    }
}

@Composable
private fun TodayTasksContent(tasks: List<Task>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        items(tasks) { task ->
            TaskItem(task = task)
        }
    }
}

