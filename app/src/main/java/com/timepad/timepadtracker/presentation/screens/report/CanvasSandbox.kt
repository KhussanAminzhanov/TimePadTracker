package com.timepad.timepadtracker.presentation.screens.report

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val textPaint = remember {
        Paint().apply {
            color = textColor.toArgb()
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val fontSizeVerticalLabel = 14.sp.toPx()
        val fontSizeHorizontalLabel = 16.sp.toPx()
        val chartStartPadding = fontSizeVerticalLabel * 5 + 5.dp.toPx()
        val horizontalPadding = 16.dp.toPx()
        val verticalPadding = 24.dp.toPx()
        val numberOfHorizontalLines = data.size
        val numberOfVerticalLines = 6
        var verticalLineSpacing: Float
        var horizontalLineSpacing: Float

        val showHelpLines = false
        val showHelpBox = false
        val showHelpChartBox = false


        //VERTICAL AXIS
        inset(
            horizontal = horizontalPadding,
            vertical = verticalPadding
        ) {
            verticalLineSpacing = size.height / numberOfVerticalLines

            var position: Float
            var verticalLabelText: String
            val maxHour = 4 * 60 * 60 * 1000L
            val stepLong = 48 * 60 * 1000L

            for (i in 0 until numberOfVerticalLines) {
                position = verticalLineSpacing * i
                verticalLabelText = (maxHour - stepLong * i).formatTimeMillisHM()

                //HELPING LINES
                if (showHelpLines) {
                    drawLine(
                        color = Purple,
                        start = Offset(x = 0F, y = position),
                        end = Offset(x = size.width, y = position),
                        strokeWidth = 1.dp.toPx()
                    )
                }

                //VERTICAL LABELS
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        verticalLabelText,
                        0f,
                        position + fontSizeVerticalLabel,
                        textPaint.apply {
                            textSize = fontSizeVerticalLabel
                            textAlign = Paint.Align.LEFT
                        }
                    )
                }

                //DOTTED LINE
                drawLine(
                    color = lineColor,
                    start = Offset(
                        x = chartStartPadding - horizontalPadding,
                        y = position + fontSizeVerticalLabel / 2
                    ),
                    end = Offset(x = size.width, y = position + fontSizeVerticalLabel / 2),
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
            left = 75.dp.toPx(),
            top = 24.dp.toPx(),
            right = 16.dp.toPx(),
            bottom = 24.dp.toPx()
        ) {
            horizontalLineSpacing = size.width / numberOfHorizontalLines
            var position: Float
            var horizontalLabelText: String

            for (i in 0 until numberOfHorizontalLines) {
                val hour = 4 * (i + 1)
                position = horizontalLineSpacing * i
                horizontalLabelText = if (hour > 12) "${hour - 12}pm" else "${hour}am"

                //HELP LINES
                if (showHelpLines) {
                    drawLine(
                        color = Green,
                        start = Offset(x = position, y = 0F),
                        end = Offset(x = position, y = size.height)
                    )
                }

                //HORIZONTAL LABELS
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        horizontalLabelText,
                        position + horizontalLineSpacing / 2,
                        size.height,
                        textPaint.apply {
                            textSize = fontSizeHorizontalLabel
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }

            //HELPING BOX
            if (showHelpChartBox) {
                drawRect(color = Green, alpha = 0.4f)
            }
        }

        val height = size.height - verticalPadding * 2
        val verticalSpacing = height / numberOfVerticalLines
        inset(
            left = chartStartPadding,
            top = verticalPadding + fontSizeVerticalLabel / 2,
            right = horizontalPadding,
            bottom = verticalPadding + verticalSpacing - fontSizeVerticalLabel / 2
        ) {

            //HELPING BOX
            if (showHelpBox) {
                drawRect(color = Black, alpha = 0.1f)
            }
        }
    }
}

@Composable
@Preview(widthDp = 343, heightDp = 312)
fun CanvasSandboxPreview() {
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
