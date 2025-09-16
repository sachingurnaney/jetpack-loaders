package io.github.sachingurnaney.jetpackloaders

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Base configuration class for all loaders.
 *
 * Can be extended for specialized loaders.
 */
open class BaseLoaderConfig(
    open val size: Dp = 48.dp,
    open val color: Color = Color.Blue,
    open val speed: Float = 1f
)

/**
 * Configuration for loaders composed of multiple elements (dots, bars, waves, etc.).
 *
 * @property elementSize Size of each individual element.
 * @property gap Spacing between elements.
 */
data class ElementLoaderConfig(
    override val size: Dp = 48.dp,
    override val color: Color = Color.Blue,
    override val speed: Float = 1f,
    val elementSize: Dp = 8.dp,
    val gap: Dp = 8.dp
) : BaseLoaderConfig(size, color, speed)

/**
 * Configuration for ring-based loaders (spinner, ring, ripple).
 *
 * @property strokeWidth The current stroke width.
 * @property minStrokeWidth Minimum stroke width (for animated strokes).
 * @property maxStrokeWidth Maximum stroke width (for animated strokes).
 */
data class RingLoaderConfig(
    override val size: Dp = 48.dp,
    override val color: Color = Color.Blue,
    override val speed: Float = 1f,
    val strokeWidth: Dp = 4.dp,
    val minStrokeWidth: Dp = 2.dp,
    val maxStrokeWidth: Dp = 6.dp
) : BaseLoaderConfig(size, color, speed)

/**
 * Predefined loader styles available in the library.
 */
enum class LoaderStyle {
    Spinner,
    Dots,
    Bars,
    Pulse,
    Ring,
    Orbit,
    Wave,
    Ripple,
    ZigZag,
    ScaleDots
}

/**
 * Generic composable loader function.
 *
 * Selects and displays a loader based on [style] and [config].
 *
 * @param style The loader style to display.
 * @param modifier Modifier for sizing, padding, etc.
 * @param config Loader configuration specific to the style.
 */
@Composable
fun Loader(
    style: LoaderStyle,
    modifier: Modifier = Modifier,
    config: BaseLoaderConfig = BaseLoaderConfig()
) {
    when (style) {
        LoaderStyle.Spinner -> SpinnerLoader(
            modifier,
            config as? RingLoaderConfig ?: RingLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Dots -> DotsLoader(
            modifier,
            config as? ElementLoaderConfig ?: ElementLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.ScaleDots -> ScaleDotsLoader(
            modifier,
            config as? ElementLoaderConfig ?: ElementLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Wave -> WaveLoader(
            modifier,
            config as? ElementLoaderConfig ?: ElementLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Bars -> BarsLoader(
            modifier,
            config as? ElementLoaderConfig ?: ElementLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Orbit -> OrbitLoader(
            modifier,
            config as? ElementLoaderConfig ?: ElementLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Ring -> RingLoader(
            modifier,
            config as? RingLoaderConfig ?: RingLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Ripple -> RippleLoader(
            modifier,
            config as? RingLoaderConfig ?: RingLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.ZigZag -> ZigZagLoader(
            modifier,
            config as? ElementLoaderConfig ?: ElementLoaderConfig(
                size = config.size,
                color = config.color,
                speed = config.speed
            )
        )

        LoaderStyle.Pulse -> PulseLoader(
            modifier,
            config // BaseLoaderConfig is sufficient
        )
    }
}