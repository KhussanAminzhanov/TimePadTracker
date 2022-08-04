package com.timepad.timepadtracker.presentation.screens.report

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatryk.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatryk.vico.compose.axis.vertical.startAxis
import com.patrykandpatryk.vico.compose.chart.Chart
import com.patrykandpatryk.vico.compose.chart.line.lineChart
import com.patrykandpatryk.vico.core.entry.ChartEntryModel
import com.patrykandpatryk.vico.core.entry.entryModelOf
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

@Composable
fun LineChartReport(
    entryModel: ChartEntryModel,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Chart(
            chart = lineChart(),
            model = entryModel,
            startAxis = startAxis(maxLabelCount = 10),
            bottomAxis = bottomAxis(),
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun LineChart(
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {

        }
    }
}

@Composable
@Preview(widthDp = 343)
fun LineChartReportPreview() {
    TimePadTheme {
        val entryModel = entryModelOf(5f, 15f, 10f, 20f, 10f)

        LineChartReport(
            entryModel = entryModel,
            modifier = Modifier
                .height(312.dp)
                .fillMaxWidth()
                .padding(all = 8.dp)
        )
    }
}

@Composable
@Preview
fun LineChartPreview() {
    TimePadTheme {
        LineChart(
            modifier = Modifier
                .width(343.dp)
                .height(312.dp)
        )
    }
}