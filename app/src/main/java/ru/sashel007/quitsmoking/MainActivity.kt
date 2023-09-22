package ru.sashel007.quitsmoking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.sashel007.quitsmoking.mainscreen.MainScreen
import ru.sashel007.quitsmoking.navigator.PageIndicator
import ru.sashel007.quitsmoking.navigator.QuitDateSelectionPage
import ru.sashel007.quitsmoking.navigator.StartingPage
import ru.sashel007.quitsmoking.ui.theme.QuitSmokingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        setContent {
            QuitSmokingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFCCCCFF)
                ) {
                    AppNavigator()
                }
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    var currentPage by remember { mutableStateOf(0) }

    DisposableEffect(navController) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                "startingPage" -> currentPage = 0
                "quitDateSelectionPage" -> currentPage = 1
            }
        }
        navController.addOnDestinationChangedListener(listener)
        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "startingPage") {
            composable("startingPage") {
                StartingPage(navController) {
                    navController.navigate("quitDateSelectionPage")
                }
            }
            composable("quitDateSelectionPage") {
                QuitDateSelectionPage(navController) {
                    // Your logic here, e.g., navigate to another destination.
                    navController.navigate("nextDestination")
                }
            }
            composable("mainScreen") {
                MainScreen()
            }
        }
        PageIndicator(currentPage = currentPage, numberOfPages = 2, Modifier.align(Alignment.BottomCenter))
    }
}