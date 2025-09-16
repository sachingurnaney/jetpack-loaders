package io.github.sachingurnaney.jetpackloaders.sample.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sachingurnaney.jetpackloaders.BaseLoaderConfig
import io.github.sachingurnaney.jetpackloaders.CustomLoaderConfig
import io.github.sachingurnaney.jetpackloaders.CustomLoaderType
import io.github.sachingurnaney.jetpackloaders.ElementLoaderConfig
import io.github.sachingurnaney.jetpackloaders.Loader
import io.github.sachingurnaney.jetpackloaders.LoaderStyle
import io.github.sachingurnaney.jetpackloaders.RingLoaderConfig
import io.github.sachingurnaney.jetpackloaders.sample.R
import io.github.sachingurnaney.jetpackloaders.sample.allLoaderItems
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoaderDetailScreen(styleName: String) {
    val item = allLoaderItems.find { it.style.name == styleName }
    if (item == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Loader not found", style = MaterialTheme.typography.bodyLarge)
        }
        return
    }

    val defaults = getDefaultValues(item.style)

    var size by remember { mutableFloatStateOf(defaults.size) }
    var speed by remember { mutableFloatStateOf(defaults.speed) }
    var red by remember { mutableFloatStateOf(defaults.red) }
    var green by remember { mutableFloatStateOf(defaults.green) }
    var blue by remember { mutableFloatStateOf(defaults.blue) }
    var gap by remember { mutableFloatStateOf(defaults.gap) }
    var elementSize by remember { mutableFloatStateOf(defaults.elementSize) }
    var strokeWidth by remember { mutableFloatStateOf(defaults.strokeWidth) }
    var minStroke by remember { mutableFloatStateOf(defaults.minStroke) }
    var maxStroke by remember { mutableFloatStateOf(defaults.maxStroke) }

    var customType by remember { mutableStateOf(CustomLoaderType.LOTTIE) }

    val color = Color(red / 255f, green / 255f, blue / 255f)

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopAppBar(title = { Text(item.displayName) }) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // --- Top Half: Loader Preview ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f),
                contentAlignment = Alignment.Center
            ) {
                val config: BaseLoaderConfig = when (item.style) {
                    LoaderStyle.Dots, LoaderStyle.ScaleDots, LoaderStyle.Wave,
                    LoaderStyle.Bars, LoaderStyle.Orbit, LoaderStyle.ZigZag ->
                        ElementLoaderConfig(
                            size = size.dp,
                            speed = speed,
                            color = color,
                            elementSize = elementSize.dp,
                            gap = gap.dp
                        )

                    LoaderStyle.Spinner, LoaderStyle.Ring, LoaderStyle.Ripple ->
                        RingLoaderConfig(
                            size = size.dp,
                            speed = speed,
                            color = color,
                            strokeWidth = strokeWidth.dp,
                            minStrokeWidth = minStroke.dp,
                            maxStrokeWidth = maxStroke.dp
                        )

                    LoaderStyle.Pulse ->
                        BaseLoaderConfig(size = size.dp, speed = speed, color = color)

                    LoaderStyle.Custom ->
                        CustomLoaderConfig(
                            size = size.dp,
                            speed = speed,
                            type = customType,
                            resource = when (customType) {
                                CustomLoaderType.LOTTIE -> R.raw.dots
                                CustomLoaderType.GIF -> "https://media.giphy.com/media/3oEjI6SIIHBdRxXI40/giphy.gif"
                                CustomLoaderType.SVG -> R.drawable.loader
                            }
                        )
                }

                Loader(style = item.style, config = config)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Bottom Half: Controls with Scroll ---
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Size: ${size.toInt()} dp")
                Slider(value = size, onValueChange = { size = it }, valueRange = 24f..200f)

                Text("Speed: ${"%.1f".format(speed)}x")
                Slider(value = speed, onValueChange = { speed = it }, valueRange = 0.2f..3f)

                if (item.style in listOf(
                        LoaderStyle.Dots, LoaderStyle.ScaleDots, LoaderStyle.Wave,
                        LoaderStyle.Bars, LoaderStyle.Orbit, LoaderStyle.ZigZag
                    )
                ) {
                    Text("Element Size: ${elementSize.toInt()} dp")
                    Slider(value = elementSize, onValueChange = { elementSize = it }, valueRange = 4f..32f)

                    Text("Gap: ${gap.toInt()} dp")
                    Slider(value = gap, onValueChange = { gap = it }, valueRange = 0f..32f)
                }

                if (item.style in listOf(LoaderStyle.Spinner, LoaderStyle.Ring, LoaderStyle.Ripple)) {
                    Text("Stroke Width: ${strokeWidth.toInt()} dp")
                    Slider(value = strokeWidth, onValueChange = { strokeWidth = it }, valueRange = 1f..16f)

                    Text("Min Stroke: ${minStroke.toInt()} dp")
                    Slider(value = minStroke, onValueChange = { minStroke = it }, valueRange = 1f..16f)

                    Text("Max Stroke: ${maxStroke.toInt()} dp")
                    Slider(value = maxStroke, onValueChange = { maxStroke = it }, valueRange = 1f..16f)
                }

                if (item.style != LoaderStyle.Custom) {
                    Text("Color")
                    ColorSlider("Red", red) { red = it }
                    ColorSlider("Green", green) { green = it }
                    ColorSlider("Blue", blue) { blue = it }
                }

                if (item.style == LoaderStyle.Custom) {
                    Text("Custom Loader Type")
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        CustomLoaderType.values().forEach { type ->
                            Button(
                                onClick = { customType = type },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (customType == type) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(type.name)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ColorSlider(label: String, value: Float, onValueChange: (Float) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("$label: ${value.toInt()}", style = MaterialTheme.typography.bodyMedium)
        Slider(
            value = value,
            onValueChange = onValueChange,
            valueRange = 0f..255f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Returns default values for a loader (used for reset)
fun getDefaultValues(style: LoaderStyle): LoaderDefaultValues {
    return when (style) {
        LoaderStyle.Dots, LoaderStyle.ScaleDots, LoaderStyle.Wave,
        LoaderStyle.Bars, LoaderStyle.Orbit, LoaderStyle.ZigZag ->
            LoaderDefaultValues(
                size = 96f,
                speed = 1f,
                red = 98f,
                green = 0f,
                blue = 238f,
                elementSize = 12f,
                gap = 8f
            )
        LoaderStyle.Spinner, LoaderStyle.Ring, LoaderStyle.Ripple ->
            LoaderDefaultValues(
                size = 96f,
                speed = 1f,
                red = 98f,
                green = 0f,
                blue = 238f,
                strokeWidth = 4f,
                minStroke = 2f,
                maxStroke = 6f
            )
        LoaderStyle.Pulse ->
            LoaderDefaultValues(
                size = 96f,
                speed = 1f,
                red = 98f,
                green = 0f,
                blue = 238f
            )

        LoaderStyle.Custom ->LoaderDefaultValues(
            size = 96f,
            speed = 1f,
            red = 0f,
            green = 0f,
            blue = 0f
        )
    }
}

// Data class for default values
data class LoaderDefaultValues(
    val size: Float,
    val speed: Float,
    val red: Float,
    val green: Float,
    val blue: Float,
    val gap: Float = 8f,
    val elementSize: Float = 12f,
    val strokeWidth: Float = 4f,
    val minStroke: Float = 2f,
    val maxStroke: Float = 6f
)