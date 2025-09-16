package io.github.sachingurnaney.jetpackloaders.sample.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.sachingurnaney.jetpackloaders.*
import io.github.sachingurnaney.jetpackloaders.sample.LoaderItem
import io.github.sachingurnaney.jetpackloaders.sample.R
import io.github.sachingurnaney.jetpackloaders.sample.allLoaderItems

@Composable
fun LoaderListScreen(onLoaderClick: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(allLoaderItems) { item ->
            LoaderCard(item) { onLoaderClick(item.style.name) }
        }
    }
}

@Composable
fun LoaderCard(item: LoaderItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Loader(style = item.style, config = getDefaultConfig(item.style))
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = item.displayName, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

// Returns actual loader configs (used in Loader())
fun getDefaultConfig(style: LoaderStyle): BaseLoaderConfig {
    return when (style) {
        LoaderStyle.Spinner, LoaderStyle.Ring, LoaderStyle.Ripple ->
            RingLoaderConfig()

        LoaderStyle.Dots, LoaderStyle.ScaleDots, LoaderStyle.Wave,
        LoaderStyle.Bars, LoaderStyle.Orbit, LoaderStyle.ZigZag ->
            ElementLoaderConfig()

        LoaderStyle.Pulse ->
            BaseLoaderConfig()

        LoaderStyle.Custom ->
            CustomLoaderConfig(
                type = CustomLoaderType.LOTTIE,
                resource = R.raw.dots
            )
    }
}
