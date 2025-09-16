package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.StrokeCap

@Composable
fun ZigZagLoader(
    modifier: Modifier = Modifier,
    config: ElementLoaderConfig = ElementLoaderConfig()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "zigzag")

    // Animate vertical offset
    val offset by infiniteTransition.animateFloat(
        -config.elementSize.value, config.elementSize.value,
        animationSpec = infiniteRepeatable(
            tween((400 / config.speed).toInt()),
            RepeatMode.Reverse
        ),
        label = "zigzagOffset"
    )

    Canvas(modifier = modifier.size(config.size)) {
        val centerY = size.center.y
        drawLine(
            color = config.color,
            start = Offset(0f, centerY - offset),
            end = Offset(size.width, centerY + offset),
            strokeWidth = config.elementSize.toPx(),
            cap = StrokeCap.Round
        )
    }
}