package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun WaveLoader(modifier: Modifier = Modifier, config: ElementLoaderConfig = ElementLoaderConfig()) {
    val infiniteTransition = rememberInfiniteTransition()
    val scales = (0 until 5).map { index ->
        infiniteTransition.animateFloat(
            0.3f, 1f,
            animationSpec = infiniteRepeatable(
                tween((600 / config.speed).toInt(), delayMillis = (index * 100 / config.speed).toInt()),
                RepeatMode.Reverse
            ),
            label = "wave$index"
        ).value
    }

    Row(
        modifier = modifier.width(config.size),
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
