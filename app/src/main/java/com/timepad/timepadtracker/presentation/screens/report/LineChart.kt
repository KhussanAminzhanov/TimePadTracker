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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
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

    val horizontalLines = 24
    val horizontalPadding = 16.dp
    val horizontalSpacing = 50.dp

    val timeIntervals = listOf(180, 180, 60, 30, 30, 0)
    val verticalLines = timeIntervals.size
    val verticalPadding = 24.dp
    var verticalLineSpacing: Float

    val chartStartPadding = (textSizeVertical * 5) + 5.dp

    val showHelpLines = false
    val showHelpBox = false
    val showHelpChartBox = false

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

                //HELPING LINES
                if (showHelpLines) {
                    drawLine(
                        color = Purple,
                        start = Offset(x = 0F, y = position),
                        end = Offset(x = size.width, y = position),
                        strokeWidth = 1.dp.toPx()
                    )
                }

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

            //HELPING BOX
            if (showHelpBox) {
                drawRect(color = Purple, alpha = 0.4f)
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

                //HORIZONTAL LABELS
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

                //HELP LINES
                if (showHelpLines) {
                    drawLine(
                        color = Green,
                        start = Offset(x = position, y = 0F),
                        end = Offset(x = position, y = size.height)
                    )
                }
            }

            //HELPING BOX
            if (showHelpChartBox) {
                drawRect(color = Green, alpha = 0.4f)
            }
        }

        //CHART
        val height = size.height - verticalPadding.toPx() * 2
        val verticalSpacing = height / verticalLines
        inset(
            left = chartStartPadding.toPx(),
            top = verticalPadding.toPx() + textSizeVertical.toPx() / 2,
            right = horizontalPadding.toPx(),
            bottom = verticalPadding.toPx() + verticalSpacing - textSizeVertical.toPx() / 2
        ) {
            //HELPING BOX
            if (showHelpBox) {
                drawRect(color = Black, alpha = 0.1f)
            }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxHeight()
            .width(100.dp)
    ) {
        drawRect(
            color = backGroundColor,
            size = Size(
                width = chartStartPadding.toPx(),
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

const val TEN_MINUTE = 10 * ONE_MINUTE
val data = listOf(0, 10, 43, 60, 32, 5).map { it * TEN_MINUTE }
