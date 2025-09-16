package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun SpinnerLoader(modifier: Modifier = Modifier, config: RingLoaderConfig = RingLoaderConfig()) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        0f, 360f,
        animationSpec = infiniteRepeatable(
            tween((1000 / config.speed).toInt(), easing = LinearEasing),
            RepeatMode.Restart
        ),
        label = "spinnerAngle"
    )

    val stroke by infiniteTransition.animateFloat(
        config.minStrokeWidth.value, config.maxStrokeWidth.value,
        animationSpec = infiniteRepeatable(
            tween((800 / config.speed).toInt(), easing = EaseInOutCubic),
            RepeatMode.Reverse
        ),
        label = "spinnerStroke"
    )

    Canvas(modifier = modifier.size(config.size)) {
        drawArc(
            color = config.color,
            startAngle = angle,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = stroke.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}