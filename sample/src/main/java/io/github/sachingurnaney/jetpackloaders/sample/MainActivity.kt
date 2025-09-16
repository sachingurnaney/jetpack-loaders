package io.github.sachingurnaney.jetpackloaders.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.sachingurnaney.jetpackloaders.sample.screens.LoaderDetailScreen
import io.github.sachingurnaney.jetpackloaders.sample.screens.LoaderListScreen
import io.github.sachingurnaney.jetpackloaders.sample.ui.theme.JetpackloadersTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackloadersTheme {
                val navController = rememberNavController()
                AppNavHost(navController)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackloadersTheme {
        Greeting("Android")
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "loaderList"
    ) {
        composable("loaderList") {
            LoaderListScreen(
                onLoaderClick = { styleName ->
                    navController.navigate("loaderDetail/$styleName")
                }
            )
        }
        composable("loaderDetail/{styleName}") { backStackEntry ->
            val styleName = backStackEntry.arguments?.getString("styleName")
            LoaderDetailScreen(styleName = styleName ?: "")
        }
    }
}