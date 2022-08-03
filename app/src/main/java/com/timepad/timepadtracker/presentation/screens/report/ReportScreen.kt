package com.timepad.timepadtracker.presentation.screens.report

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

@Composable
fun ReportScreen(
    reportViewModel: ReportViewModel
) {
    Column {
        ReportHeader(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
        Reports(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 42.dp)
        )
    }
}

@Composable
fun ReportHeader(
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
                .clickable { }
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
fun Reports(
    modifier: Modifier = Modifier
) {
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
            TaskCompletedContent(modifier)
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
            TimeDurationContent(modifier)
        }
    }
}

@Composable
fun ReportSection(
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
fun TaskCompletedContent(
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Text(
            text = "12",
            fontSize = 32.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
        )
    }
}

@Composable
fun TimeDurationContent(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = "1",
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
            text = "46",
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
@Preview(widthDp = 320)
fun ReportHeaderPreview() {
    TimePadTheme { ReportHeader() }
}

@Composable
@Preview
fun ReportsPreview() {
    TimePadTheme { Reports() }
}

@Composable
@Preview
fun ReportSectionPreview() {
    TimePadTheme {
        ReportSection(
            textRes = R.string.tasks_completed,
            backgroundColorRes = R.color.green,
            iconRes = R.drawable.checkmark
        ) { modifier ->
            TaskCompletedContent(modifier = modifier)
        }
    }
}

@Composable
@Preview
fun TaskCompletedContentPreview() {
    TimePadTheme { TaskCompletedContent() }
}

@Composable
@Preview
fun TimeDurationContentPreview() {
    TimePadTheme { TimeDurationContent() }
}