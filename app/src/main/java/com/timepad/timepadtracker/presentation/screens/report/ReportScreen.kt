package com.timepad.timepadtracker.presentation.screens.report

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.TaskRecord
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import org.koin.androidx.compose.getViewModel
import java.util.concurrent.TimeUnit

@Composable
fun ReportScreen(
    onBackArrowClick: () -> Unit
) {
    val reportViewModel: ReportViewModel = getViewModel()
    val taskRecords by reportViewModel.taskRecords.observeAsState()
    val selectedTab by reportViewModel.selectedTab.observeAsState()

    Column {
        ReportHeader(
            onBackArrowClick = onBackArrowClick,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
        Reports(
            taskRecords = taskRecords ?: emptyList(),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 42.dp)
        )
        Tabs(
            selectedTab = selectedTab ?: stringResource(id = R.string.day),
            onClick = { reportViewModel.setTab(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 48.dp)
                .padding(top = 40.dp)
                .height(44.dp)
        )
        ReportChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp, bottom = 16.dp)
        )
    }
}

@Composable
private fun ReportHeader(
    onBackArrowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
    ) {
        val (arrowBack, title) = createRefs()
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .clickable { onBackArrowClick() }
                .constrainAs(arrowBack) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )
        Text(
            text = stringResource(id = R.string.report),
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            modifier = Modifier
                .constrainAs(title) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
    }
}

@Composable
private fun Reports(
    taskRecords: List<TaskRecord>,
    modifier: Modifier = Modifier
) {

    val tasksCompleted = taskRecords.size
    var totalDuration: Long = 0

    taskRecords.forEach { totalDuration += it.duration }

    val hour = TimeUnit.MILLISECONDS.toHours(totalDuration)
    val minutes = TimeUnit.MILLISECONDS.toMinutes(totalDuration) - TimeUnit.HOURS.toMinutes(hour)


    ConstraintLayout(modifier = modifier.fillMaxWidth()) {
        val (taskCompleted, timeDuration) = createRefs()
        ReportSection(
            textRes = R.string.tasks_completed,
            backgroundColorRes = R.color.green,
            iconRes = R.drawable.checkmark,
            modifier = Modifier
                .constrainAs(taskCompleted) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(timeDuration.start, 8.dp)
                    width = Dimension.fillToConstraints
                }
        ) { modifier ->
            TaskCompletedContent(
                taskCompleted = tasksCompleted.toString(),
                modifier = modifier
            )
        }
        ReportSection(
            textRes = R.string.time_duration,
            backgroundColorRes = R.color.blue,
            iconRes = R.drawable.stopwatch,
            modifier = Modifier
                .constrainAs(timeDuration) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(taskCompleted.end, 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) { modifier ->
            TimeDurationContent(
                hour = hour.toString(),
                minute = minutes.toString(),
                modifier = modifier
            )
        }
    }
}

@Composable
private fun ReportSection(
    @StringRes textRes: Int,
    @ColorRes backgroundColorRes: Int,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Card(
        elevation = 0.dp,
        backgroundColor = colorResource(id = R.color.gray_light),
        modifier = modifier
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (icon, text, taskCompleted) = createRefs()
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(32.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(colorResource(id = backgroundColorRes))
                    .padding(6.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
            )
            Text(
                text = stringResource(id = textRes),
                modifier = Modifier
                    .constrainAs(text) {
                        centerVerticallyTo(icon)
                        start.linkTo(icon.end, 12.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            content(modifier = Modifier
                .constrainAs(taskCompleted) {
                    start.linkTo(parent.start, 16.dp)
                    top.linkTo(icon.bottom, 28.dp)
                    bottom.linkTo(parent.bottom, 24.dp)
                }
            )
        }
    }
}

@Composable
private fun TaskCompletedContent(
    taskCompleted: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = taskCompleted,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
        )
    }
}

@Composable
private fun TimeDurationContent(
    hour: String,
    minute: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = hour,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = "h",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.rubik_regular)),
            color = Color(0x66070417),
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = minute,
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            modifier = Modifier
                .alignByBaseline()
                .padding(start = 8.dp)
        )
        Text(
            text = "m",
            fontSize = 24.sp,
            fontFamily = FontFamily(Font(R.font.rubik_regular)),
            color = Color(0x66070417),
            modifier = Modifier.alignByBaseline()
        )
    }
}

@Composable
private fun Tabs(
    selectedTab: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.small,
        elevation = 0.dp,
        backgroundColor = Color(0xFFE9E9FF),
        modifier = modifier
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
        ) {
            val (dayText, weekText) = createRefs()
            Tab(
                selectedTab = selectedTab,
                textRes = R.string.day,
                onClick = onClick,
                modifier = Modifier
                    .constrainAs(dayText) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(weekText.start, 2.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
            Tab(
                selectedTab = selectedTab,
                textRes = R.string.week,
                onClick = onClick,
                modifier = Modifier
                    .constrainAs(weekText) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(dayText.end, 2.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )
        }
    }
}

@Composable
private fun Tab(
    @StringRes textRes: Int,
    selectedTab: String,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }

    val text = stringResource(id = textRes)
    var backgroundColor = Color.White
    var textColor = Color.Black

    if (selectedTab != text) {
        backgroundColor = Color.Transparent
        textColor = colorResource(id = R.color.text_light)
    }

    Text(
        text = text,
        color = textColor,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        modifier = modifier
            .clip(shape = MaterialTheme.shapes.small)
            .background(backgroundColor)
            .wrapContentHeight(Alignment.CenterVertically)
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick(text)
            }
    )
}

@Composable
private fun ReportChart(
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        elevation = 0.dp,
        backgroundColor = colorResource(id = R.color.gray_light),
        modifier = modifier.fillMaxSize()
    ) {

    }
}

@Composable
@Preview(widthDp = 320)
private fun ReportHeaderPreview() {
    TimePadTheme {
        ReportHeader(
            onBackArrowClick = {},
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview
private fun ReportsPreview() {
    TimePadTheme {
        Reports(
            taskRecords = emptyList(),
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(widthDp = 172)
private fun ReportSectionPreview() {
    TimePadTheme {
        ReportSection(
            textRes = R.string.tasks_completed,
            backgroundColorRes = R.color.green,
            iconRes = R.drawable.checkmark,
            modifier = Modifier.padding(8.dp)
        ) { modifier ->
            TaskCompletedContent(
                modifier = modifier,
                taskCompleted = "12"
            )
        }
    }
}

@Composable
@Preview
private fun TaskCompletedContentPreview() {
    TimePadTheme {
        TaskCompletedContent(
            taskCompleted = "12",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview
private fun TimeDurationContentPreview() {
    TimePadTheme {
        TimeDurationContent(
            hour = "1",
            minute = "46",
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(widthDp = 320)
private fun TabsPreview() {
    TimePadTheme {
        Tabs(
            selectedTab = stringResource(id = R.string.day),
            onClick = {},
            modifier = Modifier
                .padding(8.dp)
                .height(44.dp)
                .fillMaxWidth()
        )
    }
}

@Composable
@Preview(widthDp = 132)
private fun TabPreview() {
    TimePadTheme {
        Tab(
            textRes = R.string.day,
            selectedTab = stringResource(id = R.string.day),
            onClick = {}
        )
    }
}

@Composable
@Preview(widthDp = 343, heightDp = 312)
private fun ReportChartPreview() {
    TimePadTheme {
        ReportChart(
            modifier = Modifier
                .padding(8.dp)
        )
    }
}