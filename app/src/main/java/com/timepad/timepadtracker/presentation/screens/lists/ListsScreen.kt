package com.timepad.timepadtracker.presentation.screens.lists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.domain.TaskList
import com.timepad.timepadtracker.presentation.screens.report.Header
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel

@Composable
fun ListsScreen(
    mainNavController: NavHostController,
    mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(84.dp)
            .background(MaterialTheme.colors.background)
    ) {
        Header(
            titleTextRes = R.string.lists,
            onBackArrowClick = { mainNavController.popBackStack() },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
    }
}

@Composable
fun List(
    list: TaskList,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.height(40.dp)
    ) {
        ConstraintLayout(
            modifier = modifier.fillMaxSize()
        ) {
            val (icon, name, tasksLeft) = createRefs()
            Icon(
                painter = painterResource(id = list.iconRes),
                contentDescription = null,
                tint = colorResource(id = list.colorRes),
                modifier = Modifier.constrainAs(icon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(
                text = list.name,
                modifier = Modifier.constrainAs(name) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(icon.end, 16.dp)
                }
            )
        }
    }
}

@Composable
@Preview(widthDp = 320)
fun ListPreview() {
    val list = TaskList(
        id = 0,
        name = "Work",
        iconRes = R.drawable.ic_desktop,
        colorRes = R.color.purple_500
    )
    TimePadTheme {
        List(
            modifier = Modifier.padding(8.dp),
            list = list
        )
    }
}