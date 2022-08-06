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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timepad.timepadtracker.presentation.theme.Green
import com.timepad.timepadtracker.presentation.theme.Purple
import com.timepad.timepadtracker.presentation.theme.PurpleLight
import com.timepad.timepadtracker.presentation.theme.TimePadTheme

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

        val fontSize = 14.sp.toPx()

        //Horizontal Axis
        inset(
            left = 75.dp.toPx(),
            top = 24.dp.toPx(),
            right = 16.dp.toPx(),
            bottom = 24.dp.toPx()
        ) {
            val numberOfLines = 5
            val spaceBetweenLines = size.width / numberOfLines
            var position: Float
            for (i in 0 until numberOfLines) {
                position = spaceBetweenLines * i
                drawLine(
                    color = Green,
                    start = Offset(x = position, y = 0F),
                    end = Offset(x = position, y = size.height)
                )
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "8am",
                        position + spaceBetweenLines / 2,
                        size.height,
                        Paint().apply {
                            textSize = fontSize
                            color = Green.toArgb()
                            textAlign = Paint.Align.CENTER
                        }
                    )
                }
            }
//            drawRect(color = Green, alpha = 0.4f)
        }


        // Vertical Axis
        inset(
            left = 16.dp.toPx(),
            top = 24.dp.toPx(),
            right = 16.dp.toPx(),
            bottom = 60.dp.toPx()
        ) {
            val numberOfLines = 6
            val spaceBetweenLines = (size.height) / numberOfLines
            var position: Float
            for (i in 0 until numberOfLines) {
                position = spaceBetweenLines * i
                drawLine(
                    color = Purple,
                    start = Offset(x = 0F, y = position),
                    end = Offset(x = size.width, y = position)
                )
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "2h30m",
                        0f,
                        position + fontSize,
                        Paint().apply {
                            textSize = fontSize
                            color = Purple.toArgb()
                            textAlign = Paint.Align.LEFT
                        }
                    )
                }
            }
//            drawRect(color = Purple, alpha = 0.4f)
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
