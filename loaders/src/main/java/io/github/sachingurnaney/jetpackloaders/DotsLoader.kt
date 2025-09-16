package io.github.sachingurnaney.jetpackloaders

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun DotsLoader(modifier: Modifier = Modifier, config: ElementLoaderConfig = ElementLoaderConfig()) {
    val infiniteTransition = rememberInfiniteTransition()
    val scales = listOf(
        infiniteTransition.animateFloat(0.3f, 1f,
            animationSpec = infiniteRepeatable(tween((500 / config.speed).toInt()), RepeatMode.Reverse),
            label = "dot1").value,
        infiniteTransition.animateFloat(1f, 0.3f,
            animationSpec = infiniteRepeatable(tween((500 / config.speed).toInt()), RepeatMode.Reverse),
            label = "dot2").value,
        infiniteTransition.animateFloat(0.3f, 1f,
            animationSpec = infiniteRepeatable(tween((500 / config.speed).toInt()), RepeatMode.Reverse),
            label = "dot3").value
    )

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
