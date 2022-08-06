package com.timepad.timepadtracker.presentation.screens.report

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timepad.timepadtracker.presentation.theme.*

@Composable
fun CanvasSandbox(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {

        //Draw Start Axis Labels
        val rightPadding = size.width - 16.dp.toPx() - 44.dp.toPx()
        inset(
            left = 16.dp.toPx(),
            top = 24.dp.toPx(),
            right = rightPadding,
            bottom = 70.dp.toPx()
        ) {
            drawRect(color = Purple)
        }

        //Draw Bottom Axis Labels
        val topPaddingLabel = size.height - 24.dp.toPx() - 20.dp.toPx()
        inset(
            left = 78.dp.toPx(),
            top = topPaddingLabel,
            right = 16.dp.toPx(),
            bottom = 24.dp.toPx()
        ) {
            drawRect(color = Purple)
        }

        // Chart Paddings
        val numberOfLines = 100
        val startPadding = 78.dp.toPx()
        val endPadding = 16.dp.toPx()
        val topPadding = 24.dp.toPx()
        val bottomPadding = 70.dp.toPx()

        //Draw Horizontal Lines
        inset(
            left = startPadding,
            top = topPadding,
            bottom = bottomPadding,
            right = endPadding
        ) {
            val spaceBetweenLinesY = size.height / numberOfLines
            val spaceBetweenLinesX = size.width / numberOfLines
            for (i in (0..numberOfLines)) {
                drawLine(
                    color = Purple,
                    start = Offset(x = 0F, y = spaceBetweenLinesY * i),
                    end = Offset(x = size.width, y = spaceBetweenLinesY * i),
                    cap = StrokeCap.Round
                )

                drawLine(
                    color = Purple,
                    start = Offset(x = spaceBetweenLinesX * i, y = 0F),
                    end = Offset(x = spaceBetweenLinesX * i, y = size.height),
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }
        }
    }
}

@Composable
fun Chart(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {

        val fontSizeVerticalLabel = 14.sp.toPx()
        val fontSizeHorizontalLabel = 16.sp.toPx()
        val chartStartPadding = fontSizeVerticalLabel * 5 + 5.dp.toPx()
        val horizontalPadding = 16.dp.toPx()
        val verticalPadding = 24.dp.toPx()
        val numberOfVerticalLines = 8
        val numberOfHorizontalLines = 6
        var verticalLineSpacing: Float
        var horizontalLineSpacing: Float

        val showLines = true
        val showBox = false

        //VERTICAL AXIS
        inset(
            horizontal = horizontalPadding,
            vertical = verticalPadding
        ) {
            verticalLineSpacing = size.height / numberOfVerticalLines
            var position: Float
            for (i in 0 until numberOfVerticalLines) {
                position = verticalLineSpacing * i

                //HELPING LINES
                if (showLines) {
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
                        "4h00m",
                        0f,
                        position + fontSizeVerticalLabel,
                        Paint().apply {
                            textSize = fontSizeVerticalLabel
                            color = Purple.toArgb()
                            textAlign = Paint.Align.LEFT
                        }
                    )
                }
                //DOTTED LINE
                drawLine(
                    color = Black,
                    alpha = 0.1f,
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
            if (showBox) {
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
            for (i in 0 until numberOfHorizontalLines) {
                position = horizontalLineSpacing * i

                //HELP LINES
                if (showLines) {
                    drawLine(
                        color = Green,
                        start = Offset(x = position, y = 0F),
                        end = Offset(x = position, y = size.height)
                    )
                }

                //HORIZONTAL LABELS
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "8am",
                        position + horizontalLineSpacing / 2,
                        size.height,
                        Paint().apply {
                            textSize = fontSizeHorizontalLabel
                            color = Green.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }

            //HELPING BOX
            if (showBox) {
                drawRect(color = Green, alpha = 0.4f)
            }
        }

        val height = size.height - verticalPadding * 2
        val width = size.width - horizontalPadding * 2
        val verticalSpacing = height / numberOfVerticalLines
        inset(
            left = chartStartPadding,
            top = verticalPadding + fontSizeVerticalLabel / 2,
            right = horizontalPadding,
            bottom = verticalPadding + verticalSpacing - fontSizeVerticalLabel / 2
        ) {

            //HELPING BOX
            if (!showBox) {
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
            Chart()
        }
    }
}
