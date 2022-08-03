package com.timepad.timepadtracker.presentation.screens.report

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import kotlinx.coroutines.NonDisposableHandle.parent

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
fun TasksCompleted(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.width(164.dp)) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (icon, text, taskCompleted) = createRefs()
            Icon(
                painter = painterResource(id = R.drawable.checkmark),
                contentDescription = null,
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .size(32.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(colorResource(id = R.color.green))
                    .padding(6.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top, 16.dp)
                        start.linkTo(parent.start, 16.dp)
                    }
            )
            Text(
                text = stringResource(id = R.string.tasks_completed),
                modifier = Modifier
                    .constrainAs(text) {
                        centerVerticallyTo(icon)
                        start.linkTo(icon.end, 12.dp)
                        end.linkTo(parent.end, 16.dp)
                        width = Dimension.fillToConstraints
                    }
            )
            Text(
                text = "12",
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.rubik_medium)),
                modifier = Modifier
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
@Preview(widthDp = 320)
fun ReportHeaderPreview() {
    TimePadTheme { ReportHeader() }
}

@Composable
@Preview(widthDp = 164, heightDp = 132)
fun TasksCompletedPreview() {
    TimePadTheme { TasksCompleted() }
}
