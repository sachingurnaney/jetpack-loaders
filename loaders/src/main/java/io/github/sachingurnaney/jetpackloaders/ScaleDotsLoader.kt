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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ScaleDotsLoader(modifier: Modifier = Modifier, config: ElementLoaderConfig = ElementLoaderConfig()) {
    val infiniteTransition = rememberInfiniteTransition()
    val scales = (0 until 3).map { index ->
        infiniteTransition.animateFloat(
            0.3f, 1f,
            animationSpec = infiniteRepeatable(
                tween((500 / config.speed).toInt(), delayMillis = (index * 150 / config.speed).toInt()),
                RepeatMode.Reverse
            ),
            label = "scaleDot$index"
        ).value
    }

    Row(modifier = modifier.wrapContentSize(), horizontalArrangement = Arrangement.spacedBy(config.gap)) {
        scales.forEach { scale ->
            Box(
                modifier = Modifier
                    .size(config.elementSize)
                    .graphicsLayer { scaleX = scale; scaleY = scale }
                    .clip(CircleShape)
                    .background(config.color)
            )
        }
    }
}