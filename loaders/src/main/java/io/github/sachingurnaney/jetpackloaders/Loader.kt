package io.github.sachingurnaney.jetpackloaders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.SvgDecoder
import coil.request.ImageRequest.Builder
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

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
 * Configuration for Custom Loader.
 *
 * Allows using Lottie animations, GIFs, or custom drawables.
 *
 * @property type Type of custom loader (Lottie, SVG, GIF).
 * @property resource Any identifier (raw resource ID, URL, or painter).
 */
data class CustomLoaderConfig(
    override val size: Dp = 48.dp,
    override val color: Color = Color.Unspecified, // Not all types may use color
    override val speed: Float = 1f,
    val type: CustomLoaderType,
    val resource: Any // e.g. rawResId, URL, or painter
) : BaseLoaderConfig(size, color, speed)

/**
 * Supported custom loader types.
 */
enum class CustomLoaderType {
    LOTTIE,
    SVG,
    GIF
}


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
    ScaleDots,
    Custom // For user-provided custom loaders
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

        LoaderStyle.Custom -> {
            if (config is CustomLoaderConfig) {
                when (config.type) {
                    CustomLoaderType.LOTTIE -> {
                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(config.resource as Int)
                        )
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = modifier.size(config.size)
                        )
                    }

                    CustomLoaderType.SVG,
                    CustomLoaderType.GIF -> {
                        CustomLoader(config, modifier)
                    }
                }
            } else {
                Text(
                    text = "CustomLoaderConfig required for LoaderStyle.Custom",
                    modifier = modifier
                )
            }
        }
    }
}


@Composable
fun CustomLoader(config: CustomLoaderConfig, modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components {
                add(SvgDecoder.Factory())
                add(GifDecoder.Factory())
            }
            .build()
    }

    val painter = rememberAsyncImagePainter(
        model = Builder(context)
            .data(config.resource)
            .build(),
        imageLoader = imageLoader
    )

    Image(
        painter = painter,
        contentDescription = "Custom Loader",
        modifier = modifier.size(config.size)
    )
}