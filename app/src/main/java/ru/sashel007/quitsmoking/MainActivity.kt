package ru.sashel007.quitsmoking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.sashel007.quitsmoking.dto.AppDatabase
import ru.sashel007.quitsmoking.mainscreen.MainScreen
import ru.sashel007.quitsmoking.navigator.CigarettesInPackPage
import ru.sashel007.quitsmoking.navigator.CigarettesPerDayPage
import ru.sashel007.quitsmoking.navigator.FirstMonthWithoutSmokingPage
import ru.sashel007.quitsmoking.navigator.NotificationsPage
import ru.sashel007.quitsmoking.navigator.PageIndicator
import ru.sashel007.quitsmoking.navigator.QuitDateSelectionPage
import ru.sashel007.quitsmoking.navigator.StartingPage
import ru.sashel007.quitsmoking.ui.theme.QuitSmokingTheme

class MainActivity : ComponentActivity() {

    private var dataBase: AppDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBase = AppDatabase.getDatabase(this)
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
                "cigarettesPerDayPage" -> currentPage = 2
                "cigarettesInPackPage" -> currentPage = 3
                "packCostPage" -> currentPage = 4
                "firstMonthWithoutSmokingPage" -> currentPage = 5
                "notificationsPage" -> currentPage = 6
                "mainScreen" -> currentPage = 7  // Add this case
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
                    // Navigate to CigarettesPerDayPage after "Дальше" is clicked
                    navController.navigate("cigarettesPerDayPage")
                }
            }
            composable("cigarettesPerDayPage") {
                CigarettesPerDayPage(navController) {
                    // Example: Navigate back to quitDateSelectionPage when "<-" is clicked.
                    // Adjust based on your requirements.
                    navController.navigate("cigarettesInPackPage")
                }
            }
            composable("cigarettesInPackPage") {
                CigarettesInPackPage(navController = navController) {
                    navController.navigate("packCostPage")
                }
            }
            composable("packCostPage") {
                ru.sashel007.quitsmoking.navigator.PackCostPage(navController = navController) {
                    navController.navigate("firstMonthWithoutSmokingPage")
                }
            }
            composable("firstMonthWithoutSmokingPage") {
                FirstMonthWithoutSmokingPage(navController = navController) {
                    navController.navigate("notificationsPage")
                }
            }
            composable("notificationsPage") {
                NotificationsPage(navController = navController) {
                    navController.navigate("mainScreen")
                }
            }
            composable("mainScreen") {
                MainScreen()
            }
        }
        if (currentPage != 7) { // Assuming "mainScreen" corresponds to a value of 7
            PageIndicator(
                currentPage = currentPage,
                numberOfPages = 7,
                Modifier.align(Alignment.BottomCenter)
            )
        }
    }
}