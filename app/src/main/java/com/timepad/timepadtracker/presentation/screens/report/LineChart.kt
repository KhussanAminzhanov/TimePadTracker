package com.timepad.timepadtracker.presentation.screens.report

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timepad.timepadtracker.presentation.theme.Purple
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.utils.formatTimeMillisHM

@Composable
fun LineChart(
    data: List<Long>,
    graphColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.large,
        modifier = modifier
    ) {
        val density = LocalDensity.current
        val textPaint = remember(density) {
            Paint().apply {
                color = android.graphics.Color.BLACK
                textAlign = Paint.Align.CENTER
                textSize = density.run { 16.sp.toPx() }
            }
        }

        Canvas(modifier = Modifier.fillMaxSize()) {

            //BottomAxis
            val startPaddingBottomAxis = 78.dp.toPx()
            val endPaddingBottomAxis = 16.dp.toPx()
            val bottomPaddingBottomAxis = 24.dp.toPx()
            val hours = (4..24 step 4).map { if (it > 12) "${it - 12}am" else "${it}pm" }
            val spacePerHour =
                (size.width - startPaddingBottomAxis - endPaddingBottomAxis) / hours.size
            (hours.indices).forEach { i ->
                val hour = hours[i]
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        hour,
                        startPaddingBottomAxis + i * spacePerHour,
                        size.height - bottomPaddingBottomAxis,
                        textPaint
                    )
                }
            }

            //StartAxis
            val timeStepDuration = 1_800_000L
            val startPaddingStartAxis = 16.dp.toPx()
            val topPaddingAxis = 24.dp.toPx()
            val bottomPaddingAxis = 60.dp.toPx()
            val timeStep = (size.height - topPaddingAxis - bottomPaddingAxis) / 9
            (0..8).forEach { i ->
                drawContext.canvas.nativeCanvas.apply {
                    val startY = (size.height - topPaddingAxis - bottomPaddingAxis) - i * timeStep
                    drawText(
                        (timeStepDuration * i).formatTimeMillisHM(),
                        startPaddingStartAxis,
                        startY,
                        textPaint.apply { textAlign = Paint.Align.LEFT }
                    )
                    drawLine(
                        start = Offset(
                            startPaddingStartAxis + (timeStepDuration * i).formatTimeMillisHM().length,
                            startY,
                        ),
                        end = Offset(
                            size.width - endPaddingBottomAxis,
                            startY
                        ),
                        color = Color.Black,
                        strokeWidth = 4f
                    )
                }
            }

            val strokeWidth = 4.dp.toPx()
            val startPaddingChart = 78.dp.toPx()
            val bottomPaddingChart = 78.dp.toPx()
            val topPaddingChart = 24.dp.toPx()

            val distance = spacePerHour
            var currentX = startPaddingChart
            val maxValue = data.maxOrNull() ?: 0
            val points = mutableListOf<PointF>()

            data.forEachIndexed { index, currentData ->
                if (data.size >= index + 1) {
                    val x0 = currentX
                    val y0 = topPaddingChart + (maxValue - currentData) * ((size.height - bottomPaddingChart - topPaddingChart) / maxValue)
                    points.add(PointF(x0, y0))
                    currentX += distance
                }
            }

            val cubicPoints1 = mutableListOf<PointF>()
            val cubicPoints2 = mutableListOf<PointF>()

            for (i in 1 until points.size) {
                cubicPoints1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
                cubicPoints2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))
            }

            val path = Path()
            path.moveTo(points.first().x, points.first().y)

            for (i in 1 until points.size) {
                path.cubicTo(
                    cubicPoints1[i - 1].x,
                    cubicPoints1[i - 1].y,
                    cubicPoints2[i - 1].x,
                    cubicPoints2[i - 1].y,
                    points[i].x,
                    points[i].y
                )
            }

            drawPath(
                path = path,
                color = graphColor,
                style = Stroke(width = strokeWidth)
            )
        }
    }
}

@Composable
@Preview(widthDp = 351, heightDp = 320)
fun LineChartPreview() {
    TimePadTheme {
        LineChart(
            data = points,
            graphColor = Purple,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

const val tenMinutes = 10 * 1000L
val points = listOf(
    0 * tenMinutes,
    95 * tenMinutes,
    0 * tenMinutes,
    95 * tenMinutes,
    0 * tenMinutes,
    95 * tenMinutes
)