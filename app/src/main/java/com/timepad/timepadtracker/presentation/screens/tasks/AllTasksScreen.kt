package com.timepad.timepadtracker.presentation.screens.tasks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timepad.timepadtracker.R
import com.timepad.timepadtracker.presentation.screens.report.Header
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

@Composable
fun AllTasksScreen() {
    Column {
        Header(
            titleTextRes = R.string.all_tasks,
            onBackArrowClick = {},
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )
    }
}

@Composable
@Preview(widthDp = 320)
fun AllTasksScreenPreview() {
    TimePadTheme {
        AllTasksScreen()
    }
}
