package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun BarsLoader(modifier: Modifier = Modifier, config: ElementLoaderConfig = ElementLoaderConfig()) {
    val infiniteTransition = rememberInfiniteTransition()
    val scales = (0 until 3).map { index ->
        infiniteTransition.animateFloat(
            0.3f, 1f,
            animationSpec = infiniteRepeatable(
                tween(
                    durationMillis = (400 / config.speed).toInt(),
                    delayMillis = (index * 150 / config.speed).toInt()
                ),
                RepeatMode.Reverse
            ),
            label = "bar$index"
        ).value
    }

    Box(
        modifier = modifier.size(config.size),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(config.gap),
            verticalAlignment = Alignment.CenterVertically
        ) {
            scales.forEach { scale ->
                Box(
                    modifier = Modifier
                        .size(width = config.elementSize, height = config.size / 2)
                        .graphicsLayer { scaleY = scale }
                        .background(config.color, RoundedCornerShape(2.dp))
                )
            }
        }
    }
}