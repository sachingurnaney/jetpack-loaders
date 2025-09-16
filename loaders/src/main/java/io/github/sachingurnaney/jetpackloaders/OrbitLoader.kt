package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun OrbitLoader(
    modifier: Modifier = Modifier,
    config: ElementLoaderConfig = ElementLoaderConfig()
) {
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        0f, 360f,
        animationSpec = infiniteRepeatable(
            tween((2000 / config.speed).toInt(), easing = LinearEasing),
            RepeatMode.Restart
        ),
        label = "orbitAngle"
    )

    Box(modifier = modifier.size(config.size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Inside Canvas lambda, toPx() is available
            val radius = config.size.toPx() / 2.5f
            val center = size.center
            val rad = Math.toRadians(angle.toDouble())
            val x = center.x + cos(rad) * radius
            val y = center.y + sin(rad) * radius

            drawCircle(
                color = config.color,
                radius = config.elementSize.toPx(),
                center = Offset(x.toFloat(), y.toFloat())
            )
        }
    }
}