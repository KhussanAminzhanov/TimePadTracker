package com.timepad.timepadtracker.presentation.screens.timer

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.screens.report.Header
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel
import com.timepad.timepadtracker.utils.formatTimeMillisHMS

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

        Text(
            text = (timeLeft.value ?: 0).formatTimeMillisHMS(),
            color = MaterialTheme.colors.onBackground,
            fontSize = 40.sp,
            fontFamily = FontFamily(Font(R.font.rubik_medium)),
            modifier = Modifier
                .constrainAs(timer) {
                    top.linkTo(header.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(finishBtn.top)
                }
        )

        TimerButton(
            textRes = R.string.finish,
            onClick = {},
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
            onClick = {},
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