package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun RippleLoader(
    modifier: Modifier = Modifier,
    config: RingLoaderConfig = RingLoaderConfig()
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ripple")

    // Animate alpha from 1 to 0
    val alpha by infiniteTransition.animateFloat(
        1f, 0f,
        animationSpec = infiniteRepeatable(
            tween((1000 / config.speed).toInt()),
            RepeatMode.Restart
        ),
        label = "rippleAlpha"
    )

    Canvas(modifier = modifier.size(config.size)) {
        val radius = size.minDimension / 2
        drawCircle(
            color = config.color.copy(alpha = alpha),
            radius = radius,
            style = Stroke(width = config.strokeWidth.toPx())
        )
    }
}