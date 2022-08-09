package com.timepad.timepadtracker.presentation.screens.report

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.timepad.timepadtracker.presentation.theme.*
import com.timepad.timepadtracker.presentation.viewmodels.MainViewModel.Companion.ONE_MINUTE
import com.timepad.timepadtracker.utils.formatTimeMillisHM

@Composable
fun Chart(
    textColor: Color,
    lineColor: Color,
    data: List<Long>,
    modifier: Modifier = Modifier
) {
    val backGroundColor = MaterialTheme.colors.surface

    val textSizeVertical = 14.dp
    val textSizeHorizontal = 16.dp
    val textPaint = remember { Paint().apply { color = textColor.toArgb() } }

    val horizontalLines = data.size
    val horizontalPadding = 16.dp
    val horizontalSpacing = 50.dp

    val timeIntervals = listOf<Long>(10, 10, 10, 10, 10, 10, 0)
    val verticalLines = timeIntervals.size
    val maxInterval = (timeIntervals.size - 1) * timeIntervals.first() * ONE_MINUTE
    val verticalPadding = 24.dp
    var verticalLineSpacing: Float

    val chartStartPadding = (textSizeVertical * 5) + 5.dp

    Canvas(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .width(horizontalSpacing * 24 + chartStartPadding + horizontalPadding)
            .fillMaxHeight()
    ) {
        //VERTICAL AXIS
        inset(
            horizontal = horizontalPadding.toPx(),
            vertical = verticalPadding.toPx()
        ) {
            verticalLineSpacing = size.height / verticalLines
            var position: Float
            for (i in verticalLines - 1 downTo 0) {
                position = verticalLineSpacing * i
                //DOTTED LINE
                drawLine(
                    color = lineColor,
                    start = Offset(
                        x = chartStartPadding.toPx() - horizontalPadding.toPx(),
                        y = position + textSizeVertical.toPx() / 2 + textSizeVertical.toPx() / 4
                    ),
                    end = Offset(
                        x = size.width,
                        y = position + textSizeVertical.toPx() / 2 + textSizeVertical.toPx() / 4
                    ),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(FloatArray(4) { 16F })
                )
            }
        }

        //HORIZONTAL AXIS
        inset(
            left = chartStartPadding.toPx(),
            top = verticalPadding.toPx(),
            right = horizontalPadding.toPx(),
            bottom = verticalPadding.toPx()
        ) {
            var position: Float
            var labelHorizontal: String
            for (i in 0 until horizontalLines) {
                position = i * horizontalSpacing.toPx()
                labelHorizontal = if (i > 12) "${i - 12}pm" else "${i}am"
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        labelHorizontal,
                        position + horizontalSpacing.toPx() / 2,
                        size.height,
                        textPaint.apply {
                            textSize = textSizeHorizontal.toPx()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
        }

        //CHART
        val height = size.height - verticalPadding.toPx() * 2
        val verticalSpacing = height / verticalLines
        inset(
            left = chartStartPadding.toPx(),
            top = verticalPadding.toPx() + textSizeVertical.toPx() / 2 + textSizeVertical.toPx() / 4,
            right = horizontalPadding.toPx(),
            bottom = verticalPadding.toPx() + verticalSpacing - textSizeVertical.toPx() / 2 - textSizeVertical.toPx() / 4
        ) {
            val points = mutableListOf<Offset>()
            val controlPointsOne = mutableListOf<Offset>()
            val controlPointsTwo = mutableListOf<Offset>()

            var percentage: Float
            var x: Float
            var y: Float

            for (i in 0 until horizontalLines) {
                percentage = data[i].toFloat() * 100 / maxInterval / 100
                x = horizontalSpacing.toPx() * i + horizontalSpacing.toPx() / 2
                y = size.height * (1f - percentage)
                points.add(Offset(x, y))
            }

            for (i in 0 until points.size - 1) {
                controlPointsOne.add(Offset((points[i].x + points[i + 1].x) / 2, points[i].y))
                controlPointsTwo.add(Offset((points[i].x + points[i + 1].x) / 2, points[i + 1].y))
            }

            val path = Path().apply {
                moveTo(points.first().x, points.first().y)
                for (i in 0 until points.size - 1) {
                    cubicTo(
                        x1 = controlPointsOne[i].x, y1 = controlPointsOne[i].y,
                        x2 = controlPointsTwo[i].x, y2 = controlPointsTwo[i].y,
                        x3 = points[i + 1].x, y3 = points[i + 1].y
                    )
                }
            }

            drawPath(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Lavender,
                        Lavender,
                        Lavender,
                        Purple,
                        Purple,
                        Purple,
                        PurpleLight
                    )
                ),
                path = path,
                style = Stroke(
                    width = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }
    }

    Canvas(modifier = Modifier.fillMaxHeight()) {
        drawRect(
            color = backGroundColor,
            size = Size(
                width = chartStartPadding.toPx() - horizontalPadding.toPx(),
                height = size.height
            )
        )
        inset(
            horizontal = horizontalPadding.toPx(),
            vertical = verticalPadding.toPx()
        ) {
            verticalLineSpacing = size.height / verticalLines
            var position: Float
            var verticalLabelText: String
            var labelTime = 0L
            for (i in verticalLines - 1 downTo 0) {
                position = verticalLineSpacing * i
                labelTime += timeIntervals[i] * ONE_MINUTE
                verticalLabelText = labelTime.formatTimeMillisHM()
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        verticalLabelText,
                        0f,
                        position + textSizeVertical.toPx(),
                        textPaint.apply {
                            textSize = textSizeVertical.toPx()
                            textAlign = Paint.Align.LEFT
                        }
                    )
                }
            }
        }

        val colors = listOf(
            backGroundColor,
            backGroundColor,
            Color.Transparent
        )
        drawRect(
            brush = Brush.horizontalGradient(
                colors = colors,
                startX = (chartStartPadding - horizontalPadding - 1.dp).toPx(),
                endX = chartStartPadding.toPx()
            ),
            topLeft = Offset(x = (chartStartPadding - horizontalPadding - 1.dp).toPx(), y = 0f),
            size = Size(width = 16.dp.toPx(), height = size.height)
        )

        drawRect(
            brush = Brush.horizontalGradient(
                colors = colors.reversed(),
                startX = size.width - horizontalPadding.toPx(),
                endX = size.width
            )
        )
    }
}

@Composable
@Preview(widthDp = 343, heightDp = 312)
fun ChartPreview() {
    TimePadTheme {
        Surface(
            shape = MaterialTheme.shapes.large,
            modifier = Modifier.padding(8.dp)
        ) {
            Chart(
                textColor = Black40,
                lineColor = Color(0x1A000000),
                data = data
            )
        }
    }
}

fun getRandom() = (0..60).random()
val data = List(24) { getRandom() * ONE_MINUTE }