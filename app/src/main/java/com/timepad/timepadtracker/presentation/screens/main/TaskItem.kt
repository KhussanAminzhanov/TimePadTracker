package com.timepad.timepadtracker.presentation.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.Task
import com.timepad.timepadtracker.presentation.theme.PurpleLight
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.utils.formatTimeMillis
import java.time.LocalDate

@Composable
fun TaskItem(
    task: Task,
    onTaskItemClick: (Task) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .height(84.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val (taskIcon, taskName, taskCategory, taskDuration, btnPlay) = createRefs()

            Image(
                painter = painterResource(id = task.iconId),
                contentDescription = null,
                modifier = Modifier
                    .height(44.dp)
                    .width(44.dp)
                    .constrainAs(taskIcon) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                    }
            )

            Text(
                text = task.name,
                fontSize = 14.sp,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .constrainAs(taskName) {
                        top.linkTo(taskIcon.top)
                        start.linkTo(taskIcon.end)
                    }
            )

            Text(
                text = task.category,
                color = Color(0xFF9B51E0),
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 13.dp)
                    .clip(shape = MaterialTheme.shapes.small)
                    .background(PurpleLight)
                    .padding(horizontal = 8.dp, vertical = 5.dp)
                    .constrainAs(taskCategory) {
                        bottom.linkTo(parent.bottom)
                        top.linkTo(taskName.bottom)
                        start.linkTo(taskIcon.end)
                    }
            )

            Text(
                text = task.duration.formatTimeMillis(),
                fontSize = 12.sp,
                color = Color(0xFF4F4F4F),
                modifier = Modifier
                    .constrainAs(taskDuration) {
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    })

            Icon(
                painter = painterResource(id = R.drawable.ic_play),
                contentDescription = null,
                tint = Color(0xFF4F4F4F),
                modifier = Modifier
                    .size(20.dp)
                    .clickable { onTaskItemClick(task) }
                    .constrainAs(btnPlay) {
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    }
            )

        }
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    val task = Task(
        id = 0,
        daySinceEpoch = LocalDate.now().toEpochDay(),
        iconId = R.drawable.icon_monitor_circle,
        category = "Work",
        name = "Work"
    )
    TimePadTheme {
        TaskItem(task = task, {})
    }
}