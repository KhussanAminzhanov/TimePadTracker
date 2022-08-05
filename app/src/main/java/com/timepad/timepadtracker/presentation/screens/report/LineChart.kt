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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timepad.timepadtracker.presentation.theme.Purple
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

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
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokeWidth = 4.dp.toPx()

            val startPadding = 78.dp.toPx()
            val bottomPadding = 24.dp.toPx()
            val topPadding = 24.dp.toPx()
            val endPadding = 16.dp.toPx()

            val chartWidth = size.width - startPadding - endPadding

            val distance = (chartWidth / data.size - 1)
            var currentX = startPadding
            val maxValue = data.maxOrNull() ?: 0
            val points = mutableListOf<PointF>()


            data.forEachIndexed { index, currentData ->
                if (data.size >= index + 1) {
                    val x0 = currentX
                    val y0 = topPadding + (maxValue - currentData) * ((size.height - bottomPadding - topPadding) / maxValue)
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

//            drawRect(
//                brush = Brush.linearGradient(colors = listOf(Color.Red, Color.Red)),
//                topLeft = Offset(points.first().x, points.last().y),
//                size = Size(points.last().x - points.first().x, points.first().y - points.last().y)
//            )

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