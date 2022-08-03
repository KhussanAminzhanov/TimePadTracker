package com.timepad.timepadtracker.presentation.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.screens.main.TaskItem
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import java.time.LocalDate

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        TodayTasksHeader(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
        TodayTasks(
            mainViewModel = mainViewModel, modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun TodayTasksHeader(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.today),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .alignByBaseline()
        )
        Text(
            text = stringResource(id = R.string.see_all),
            fontSize = 16.sp,
            modifier = Modifier
                .alignByBaseline()
        )
    }
}

@Composable
fun TodayTasks(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    val tasks by mainViewModel.tasks.observeAsState()
    tasks?.let {
        TodayTasksContent(
            tasks = it,
            modifier = modifier
        )
    }
}

@Composable
private fun TodayTasksContent(
    tasks: List<Task>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(tasks) { task ->
            TaskItem(task = task)
        }
    }
}

@Composable
@Preview
fun TodayTasksHeaderPreview() {
    TimePadTheme { TodayTasksHeader() }
}

@Composable
@Preview
fun TodayTaskContentPreview() {
    TimePadTheme { TodayTasksContent(tasks = tasksData) }
}

private val tasksData = listOf(
    Task(
        id = 0,
        daySinceEpoch = LocalDate.now().toEpochDay(),
        category = "Work",
        name = "Work",
        iconId = R.drawable.icon_monitor_circle
    ),
    Task(
        id = 0,
        daySinceEpoch = LocalDate.now().toEpochDay(),
        category = "Personal",
        name = "Study",
        iconId = R.drawable.icon_book_circle
    ),
    Task(
        id = 0,
        daySinceEpoch = LocalDate.now().toEpochDay(),
        category = "Sport",
        name = "Working out",
        iconId = R.drawable.icon_barbell_circle
    )
)
