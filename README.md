# Jetpack Loaders

A modern, customizable collection of **loading animations** for **Jetpack Compose**.  
Includes multiple loader styles such as dots, rings, bars, wave, ripple, orbit, zigzag, and more, all fully configurable.

## Features

- 10+ distinct loader styles:
  - Spinner, Dots, ScaleDots, Bars, Pulse, Ring, Orbit, Wave, Ripple, ZigZag
- Fully configurable:
  - `size` – total loader size
  - `speed` – animation speed multiplier
  - `color` – loader color
  - `elementSize` – size of individual dots/bars/waves
  - `gap` – spacing between elements
  - `strokeWidth` – for ring/spinner loaders
- Easy to use via a single `Loader` composable
- Live preview and tweaking available in the sample app

## Installation

Add the library to your Gradle dependencies:

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/sachingurnaney/jetpack-loaders")
            credentials {
                username = "<YOUR_GITHUB_USERNAME>"
                password = "<YOUR_PERSONAL_ACCESS_TOKEN>"
            }
        }
    }
}

```kotlin
dependencies {
    implementation("io.github.sachingurnaney:jetpack-loaders:1.0.0")
}

Usage

Use the generic Loader composable:

@Composable
fun ExampleLoader() {
    Loader(
        style = LoaderStyle.Dots,
        config = ElementLoaderConfig(
            size = 64.dp,
            color = Color.Magenta,
            elementSize = 12.dp,
            gap = 8.dp,
            speed = 1.5f
        )
    )
}

Sample App

The repository includes a sample app demonstrating all loader types with:
Interactive sliders for size, speed, stroke, element size, and gap
Live color picker for loader color

Contribution

Contributions are welcome! Feel free to open issues or pull requests.
