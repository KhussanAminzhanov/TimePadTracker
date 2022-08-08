package com.timepad.timepadtracker.presentation.screens.newtask

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.navigation.Screen
import com.timepad.timepadtracker.presentation.screens.report.Header
import com.timepad.timepadtracker.presentation.screens.timer.TimerButton
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@Composable
fun NewTaskScreen(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel
) {
    val taskMinutes = remember { mutableStateOf(0L) }
    val taskSeconds = remember { mutableStateOf(0L) }
    val taskName = remember { mutableStateOf("") }
    val taskCategory = remember { mutableStateOf("") }

    val buttonBackgroundColor = if (isSystemInDarkTheme()) Color(0xFF1B143F) else Color(0xFFE9E9FF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        Header(
            titleTextRes = R.string.new_task,
            onBackArrowClick = { mainNavController.popBackStack() },
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )
        TaskDuration(
            minutes = taskMinutes,
            seconds = taskSeconds,
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )
        TaskNameTextField(
            taskName = taskName,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
        TaskCategories(
            taskCategory = taskCategory,
            categories = mainViewModel.categories,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
        TimerButton(
            backgroundColor = buttonBackgroundColor,
            textRes = R.string.add_task,
            onClick = {
                if (taskCategory.value.isBlank() && taskName.value.isBlank()) return@TimerButton
                if (taskMinutes.value == 0L && taskSeconds.value == 0L) return@TimerButton
                val iconId = mainViewModel.tasksWithIcon[taskCategory.value]
                    ?: R.drawable.icon_book_circle
                val date = LocalDate.now().toEpochDay()
                val duration =
                    TimeUnit.MINUTES.toMillis(taskMinutes.value) + TimeUnit.SECONDS.toMillis(
                        taskSeconds.value
                    )
                val newTask = Task(
                    iconId = iconId,
                    daySinceEpoch = date,
                    name = taskName.value,
                    category = taskCategory.value,
                    duration = duration
                )
                mainViewModel.addTask(task = newTask)
                mainNavController.navigate(Screen.Main.route) {
                    popUpTo(mainNavController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            modifier = Modifier.padding(top = 80.dp)
        )
    }
}

@Composable
fun TaskDuration(
    minutes: MutableState<Long>,
    seconds: MutableState<Long>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth()
    ) {
        NumberTextField(
            hintTextRes = R.string.minutes,
            number = minutes,
            modifier = Modifier
                .padding(end = 8.dp)
                .fillMaxWidth(0.5F)
        )
        NumberTextField(
            hintTextRes = R.string.seconds,
            number = seconds,
            modifier = Modifier
                .padding(start = 8.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
fun NumberTextField(
    @StringRes hintTextRes: Int,
    number: MutableState<Long>,
    modifier: Modifier = Modifier
) {
    TextField(
        label = { Text(text = stringResource(id = hintTextRes)) },
        value = if (number.value == 0L) "" else number.value.toString(),
        onValueChange = { number.value = it.toLongOrNull() ?: 0 },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onSurface,
            cursorColor = MaterialTheme.colors.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colors.onSurface
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
    )
}

@Composable
fun TaskNameTextField(
    taskName: MutableState<String>,
    modifier: Modifier = Modifier
) {
    TextField(
        value = taskName.value,
        onValueChange = { taskName.value = it },
        label = {
            Text(text = stringResource(R.string.name))
        },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = MaterialTheme.colors.onSurface,
            backgroundColor = MaterialTheme.colors.surface,
            textColor = MaterialTheme.colors.onSurface,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedLabelColor = MaterialTheme.colors.onSurface
        ),
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun TaskCategories(
    taskCategory: MutableState<String>,
    categories: List<String>,
    modifier: Modifier
) {
    val surface = MaterialTheme.colors.surface
    val backgroundColor = if (isSystemInDarkTheme()) Color(0xFF1B143F) else Color(0xFFE9E9FF)

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(id = R.string.category),
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            categories.forEach {
                Button(
                    onClick = { taskCategory.value = it },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = if (taskCategory.value == it) backgroundColor else surface
                    ),
                    elevation = ButtonDefaults.elevation(0.dp, 0.dp, 0.dp),
                    modifier = Modifier.wrapContentHeight()
                ) {
                    Text(
                        text = it,
                        color = MaterialTheme.colors.onSurface
                    )
                }
            }
        }
    }
}
