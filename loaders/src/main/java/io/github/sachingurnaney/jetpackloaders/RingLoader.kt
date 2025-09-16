package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun RingLoader(modifier: Modifier = Modifier, config: RingLoaderConfig = RingLoaderConfig()) {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        0.5f, 1.2f,
        animationSpec = infiniteRepeatable(
            tween((1000 / config.speed).toInt(), easing = EaseInOutCubic),
            RepeatMode.Reverse
        ),
        label = "ringScale"
    )

    Box(
        modifier = modifier
            .size(config.size)
            .scale(scale)
            .border(config.strokeWidth, config.color, CircleShape)
    )
}