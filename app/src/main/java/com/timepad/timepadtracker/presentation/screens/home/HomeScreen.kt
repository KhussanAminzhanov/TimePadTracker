package com.timepad.timepadtracker.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.screens.main.TaskItem
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.formatTimeMillisHMS
import java.time.LocalDate

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel,
    onTaskItemClick: (Task) -> Unit,
    onSeeAllClick: () -> Unit,
    onRightArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val timeLeft by mainViewModel.timeLeftInMillis.observeAsState()
    val taskTitle = mainViewModel.getSelectedTaskTitle()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        HomeHeader(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 32.dp)
        )
        TimerCard(
            timeLeft = timeLeft ?: 0,
            taskTitle = taskTitle,
            onRightArrowClick = onRightArrowClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
        TodayTasksHeader(
            onSeeAllClick = onSeeAllClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
        TodayTasks(
            onTaskItemClick = onTaskItemClick,
            mainViewModel = mainViewModel,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )
    }
}

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = R.string.task),
//            color = MaterialTheme.colors.secondary,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Icon(
            tint = Color(0xFF828282),
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
fun TimerCard(
    timeLeft: Long,
    taskTitle: String,
    onRightArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        elevation = 0.dp,
        modifier = modifier
            .fillMaxWidth()
    ) {
        ConstraintLayout {
            val (timerDuration, timerName, timerNameIcon, rightArrow) = createRefs()

            Text(
                text = timeLeft.formatTimeMillisHMS(),
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium)),
                modifier = Modifier
                    .constrainAs(timerDuration) {
                        top.linkTo(parent.top, 12.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.ellipse),
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .constrainAs(timerNameIcon) {
                        top.linkTo(timerDuration.bottom, 26.dp)
                        start.linkTo(parent.start, 16.dp)
                        bottom.linkTo(parent.bottom, 28.dp)
                    }
            )

            Text(
                text = taskTitle,
                fontSize = 16.sp,
                modifier = Modifier
                    .constrainAs(timerName) {
                        centerVerticallyTo(timerNameIcon)
                        start.linkTo(timerNameIcon.end, 12.dp)
                    }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier
                    .clickable { onRightArrowClick() }
                    .constrainAs(rightArrow) {
                        top.linkTo(timerDuration.top)
                        bottom.linkTo(timerDuration.bottom)
                        end.linkTo(parent.end, 24.dp)
                    }
            )
        }
    }
}

@Composable
fun TodayTasksHeader(
    onSeeAllClick: () -> Unit,
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
                .clickable { onSeeAllClick() }
        )
    }
}

@Composable
fun TodayTasks(
    onTaskItemClick: (Task) -> Unit,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val tasks by mainViewModel.tasks.observeAsState()
    tasks?.let {
        TodayTasksContent(
            tasks = it,
            onTaskItemClick = onTaskItemClick,
            modifier = modifier
        )
    }
}

@Composable
private fun TodayTasksContent(
    tasks: List<Task>,
    onTaskItemClick: (Task) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(tasks) { task ->
            TaskItem(
                task = task,
                onTaskItemClick = onTaskItemClick
            )
        }
    }
}

@Composable
@Preview(widthDp = 320)
fun HomeHeaderPreview() {
    TimePadTheme { HomeHeader(modifier = Modifier.padding(8.dp)) }
}

@Composable
@Preview(widthDp = 320)
fun CurrentTimerPreview() {
    TimePadTheme {
        TimerCard(
            timeLeft = 10000,
            taskTitle = "Working out",
            onRightArrowClick = {},
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
@Preview(widthDp = 320)
fun TodayTasksHeaderPreview() {
    TimePadTheme {
        TodayTasksHeader(
            onSeeAllClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(widthDp = 320)
fun TodayTaskContentPreview() {
    TimePadTheme {
        TodayTasksContent(
            tasks = tasksData,
            onTaskItemClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
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