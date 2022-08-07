package com.timepad.timepadtracker.presentation.screens.report

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timepad.timepadtracker.presentation.theme.Black
import com.timepad.timepadtracker.presentation.theme.Green
import com.timepad.timepadtracker.presentation.theme.Purple
import com.timepad.timepadtracker.presentation.theme.TimePadTheme
import com.timepad.timepadtracker.utils.formatTimeMillisHM


@Composable
fun CanvasSandbox(
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
        val numberOfHorizontalLines = 24
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
//            horizontalLineSpacing = size.width / numberOfHorizontalLines
            horizontalLineSpacing = size.width / 6
            var position: Float
            var horizontalLabelText: String

            for (i in 0 until numberOfHorizontalLines) {
//                val hour = 4 * (i + 1)
                position = horizontalLineSpacing * i
                horizontalLabelText = if (i > 12) "${i - 12}pm" else "${i}am"

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
fun ChartTest() {

    Canvas(modifier = Modifier.fillMaxSize()) {

        val currentPoint = Offset(size.width * (1f / 3), size.height * (2f/3))
        val nextPoint = Offset(size.width * (2f / 3), size.height * (1f/3))
        val pointTwo = Offset((nextPoint.x + currentPoint.x) / 2, (currentPoint.y + nextPoint.y) / 2)

        val conPointOne = Offset(
            x = (nextPoint.x + currentPoint.x) / 2,
            y = nextPoint.y
        )

        val conPointTwo = Offset(
            x = (nextPoint.x + currentPoint.x) / 2,
            y = currentPoint.y
        )

        val path = Path().apply {
            moveTo(nextPoint.x, nextPoint.y)
            cubicTo(
                x1 = conPointOne.x, y1 = conPointOne.y,
                x2 = conPointTwo.x, y2 = conPointTwo.y, // control point
                x3 = currentPoint.x, y3 = currentPoint.y
            )
        }

        drawPath(
            path = path,
            color = Purple,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round
            )
        )

        drawCircle(
            color = Color.Blue,
            center = currentPoint,
            radius = 3.dp.toPx()
        )
        drawCircle(
            color = Color.Red,
            center = nextPoint,
            radius = 3.dp.toPx()
        )

        drawCircle(
            color = Color.Green,
            center = pointTwo,
            radius = 3.dp.toPx()
        )

        drawCircle(
            color = Color.Cyan,
            center = conPointOne,
            radius = 3.dp.toPx()
        )

        drawCircle(
            color = Color.Cyan,
            center = conPointTwo,
            radius = 3.dp.toPx()
        )
    }
}

@Composable
@Preview
fun CharTestPreview() {
    TimePadTheme {
        ChartTest()
    }
}