package io.github.sachingurnaney.jetpackloaders.sample.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import io.github.sachingurnaney.jetpackloaders.ElementLoaderConfig
import io.github.sachingurnaney.jetpackloaders.Loader
import io.github.sachingurnaney.jetpackloaders.LoaderStyle
import io.github.sachingurnaney.jetpackloaders.RingLoaderConfig
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

    // Default configuration
    val defaultConfig = remember(item.style) { mutableStateOf(getDefaultValues(item.style)) }

    // State for configuration
    var size by remember { mutableFloatStateOf(defaultConfig.value.size) }
    var speed by remember { mutableFloatStateOf(defaultConfig.value.speed) }
    var red by remember { mutableFloatStateOf(defaultConfig.value.red) }
    var green by remember { mutableFloatStateOf(defaultConfig.value.green) }
    var blue by remember { mutableFloatStateOf(defaultConfig.value.blue) }
    var gap by remember { mutableFloatStateOf(defaultConfig.value.gap) }
    var elementSize by remember { mutableFloatStateOf(defaultConfig.value.elementSize) }
    var strokeWidth by remember { mutableFloatStateOf(defaultConfig.value.strokeWidth) }
    var minStroke by remember { mutableFloatStateOf(defaultConfig.value.minStroke) }
    var maxStroke by remember { mutableFloatStateOf(defaultConfig.value.maxStroke) }

    val color = Color(red / 255f, green / 255f, blue / 255f)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(item.displayName) },
                actions = {
                    TextButton(onClick = {
                        // Reset all values to default
                        size = defaultConfig.value.size
                        speed = defaultConfig.value.speed
                        red = defaultConfig.value.red
                        green = defaultConfig.value.green
                        blue = defaultConfig.value.blue
                        gap = defaultConfig.value.gap
                        elementSize = defaultConfig.value.elementSize
                        strokeWidth = defaultConfig.value.strokeWidth
                        minStroke = defaultConfig.value.minStroke
                        maxStroke = defaultConfig.value.maxStroke
                    }) {
                        Text("Reset")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()) // Make it scrollable
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Preview Area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // fixed height for visibility
                contentAlignment = Alignment.Center
            ) {
                val config: BaseLoaderConfig = when (item.style) {
                    LoaderStyle.Dots, LoaderStyle.ScaleDots, LoaderStyle.Wave,
                    LoaderStyle.Bars, LoaderStyle.Orbit, LoaderStyle.ZigZag -> {
                        ElementLoaderConfig(
                            size = size.dp,
                            speed = speed,
                            color = color,
                            elementSize = elementSize.dp,
                            gap = gap.dp
                        )
                    }

                    LoaderStyle.Spinner, LoaderStyle.Ring, LoaderStyle.Ripple -> {
                        RingLoaderConfig(
                            size = size.dp,
                            speed = speed,
                            color = color,
                            strokeWidth = strokeWidth.dp,
                            minStrokeWidth = minStroke.dp,
                            maxStrokeWidth = maxStroke.dp
                        )
                    }

                    LoaderStyle.Pulse -> {
                        BaseLoaderConfig(
                            size = size.dp,
                            speed = speed,
                            color = color
                        )
                    }
                }

                Loader(style = item.style, config = config)
            }

            // Controls
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Size: ${size.toInt()} dp")
                Slider(value = size, onValueChange = { size = it }, valueRange = 24f..200f)

                Text("Speed: ${"%.1f".format(speed)}x")
                Slider(value = speed, onValueChange = { speed = it }, valueRange = 0.2f..3f)

                Text("Color")
                ColorSlider("Red", red) { red = it }
                ColorSlider("Green", green) { green = it }
                ColorSlider("Blue", blue) { blue = it }

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