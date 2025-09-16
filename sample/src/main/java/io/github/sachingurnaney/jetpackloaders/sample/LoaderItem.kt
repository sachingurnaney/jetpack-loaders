package io.github.sachingurnaney.jetpackloaders.sample

import io.github.sachingurnaney.jetpackloaders.LoaderStyle

data class LoaderItem(
    val style: LoaderStyle,
    val displayName: String
)

val allLoaderItems = listOf(
    LoaderItem(LoaderStyle.Spinner, "Spinner"),
    LoaderItem(LoaderStyle.Dots, "Dots"),
    LoaderItem(LoaderStyle.Bars, "Bars"),
    LoaderItem(LoaderStyle.Pulse, "Pulse"),
    LoaderItem(LoaderStyle.Ring, "Ring"),
    LoaderItem(LoaderStyle.Orbit, "Orbit"),
    LoaderItem(LoaderStyle.Wave, "Wave"),
    LoaderItem(LoaderStyle.Ripple, "Ripple"),
    LoaderItem(LoaderStyle.ZigZag, "ZigZag"),
    LoaderItem(LoaderStyle.ScaleDots, "Scale Dots"),
    LoaderItem(LoaderStyle.Custom, "Lottie Dots")
)
