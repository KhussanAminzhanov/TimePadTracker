package com.timepad.timepadtracker.presentation.screens.timer

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.screens.report.Header
import com.timepad.timepadtracker.presentation.theme.LavenderLight
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.formatTimeMillisHMS

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TimerScreen(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel
) {
    val timeLeft = mainViewModel.timeLeftInMillis.observeAsState()

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        val (header, timer, finishBtn, quitBtn) = createRefs()
        val backgroundColor = if (isSystemInDarkTheme()) Color(0xFF1B143F) else Color(0xFFE9E9FF)
        val primaryBarColor = Brush.verticalGradient(listOf(Color(0xFFc198e9), LavenderLight))

        Header(
            titleTextRes = R.string.timer,
            onBackArrowClick = { mainNavController.popBackStack() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
                .constrainAs(header) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        CircularProgressBar(
            number = timeLeft.value ?: 0,
            percentage = mainViewModel.getTimeLeftPercentage(),
            radius = 120.dp,
            primaryBarColor = primaryBarColor,
            secondBarColor = backgroundColor,
            modifier = Modifier
                .constrainAs(timer) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(finishBtn.top)
                }
                .combinedClickable(
                    onClick = { mainViewModel.startOrPauseTimer() },
                    onLongClick = { mainViewModel.stopTimer() }
                )
        )

        TimerButton(
            textRes = R.string.finish,
            onClick = { mainViewModel.onTimerFinish() },
            backgroundColor = backgroundColor,
            modifier = Modifier
                .constrainAs(finishBtn) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(quitBtn.top)
                }
        )

        TimerButton(
            textRes = R.string.quit,
            onClick = { mainViewModel.stopTimer() },
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.onSurface.copy(0.7f),
            modifier = Modifier
                .constrainAs(quitBtn) {
                    start.linkTo(parent.start, 40.dp)
                    end.linkTo(parent.end, 40.dp)
                    bottom.linkTo(parent.bottom, 80.dp)
                }
        )
    }
}

@Composable
fun TimerButton(
    @StringRes textRes: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    contentColor: Color = MaterialTheme.colors.onSurface
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
            .height(60.dp)
    ) {
        Text(
            fontSize = 18.sp,
            text = stringResource(id = textRes),
        )
    }
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Long,
    radius: Dp,
    primaryBarColor: Brush,
    secondBarColor: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 18.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )

    Log.e("TimerScreen", "Time Left: $number Percentage: $percentage")
    LaunchedEffect(key1 = true) { animationPlayed = true }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.size(radius * 2f)
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = secondBarColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth.toPx())
            )
            drawArc(
                brush = primaryBarColor,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
        Text(
            text = number.formatTimeMillisHMS(),
            color = MaterialTheme.colors.onBackground,
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
        )
    }
}