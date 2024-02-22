package ru.sashel007.quitsmoking

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.sashel007.quitsmoking.dto.user.AppDatabase
import ru.sashel007.quitsmoking.dto.user.UserDao
import ru.sashel007.quitsmoking.mainscreen.MainScreen
import ru.sashel007.quitsmoking.navigator.CigarettesInPackPage
import ru.sashel007.quitsmoking.navigator.CigarettesPerDayPage
import ru.sashel007.quitsmoking.navigator.FirstMonthWithoutSmokingPage
import ru.sashel007.quitsmoking.navigator.NotificationsPage
import ru.sashel007.quitsmoking.navigator.PackCostPage
import ru.sashel007.quitsmoking.navigator.PageIndicator
import ru.sashel007.quitsmoking.navigator.QuitDateSelectionPage
import ru.sashel007.quitsmoking.navigator.StartingPage
import ru.sashel007.quitsmoking.ui.theme.QuitSmokingTheme
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

class MainActivity : ComponentActivity() {

    private var dataBase: AppDatabase? = null
    private lateinit var userDao: UserDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBase = AppDatabase.getDatabase(this)
        userDao = dataBase!!.userDao()
        AndroidThreeTen.init(this)

        setContent {
            QuitSmokingTheme {
                val userViewModel: UserViewModel = viewModel()

                LaunchedEffect(key1 = Unit) {
                    userViewModel.allUserData.observeForever { userDataList ->
                        userDataList.forEach { userData ->
                            Log.d("DatabaseData", userData.toString())
                        }
                    }
                }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AppNavigator(userViewModel)
                }
            }
        }
    }
}

@Composable
fun AppNavigator(userViewModel: UserViewModel) {
    val navController = rememberNavController()
    var currentPage by remember { mutableIntStateOf(0) }

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
        NavHost(
            navController = navController,
            startDestination = "startingPage",
            enterTransition = { forwardTransitionAnimation() },
            exitTransition = { backwardTransitionAnimation() },
            popEnterTransition = { popForwardTransitionAnimation() },
            popExitTransition = { popBackwardTransitionAnimation() }
        ) {
            composable(route = "startingPage") {
                StartingPage {
                    navController.navigate("quitDateSelectionPage")
                }
            }
            composable(route = "quitDateSelectionPage") {
                QuitDateSelectionPage(
                    viewModel = userViewModel,
                    onClickForward = { navController.navigate("cigarettesPerDayPage") },
                    navController = navController,
                )
            }
            composable(route = "cigarettesPerDayPage") {
                CigarettesPerDayPage {
                    navController.navigate("cigarettesInPackPage")
                }
            }
            composable(route = "cigarettesInPackPage") {
                CigarettesInPackPage {
                    navController.navigate("packCostPage")
                }
            }
            composable(route = "packCostPage") {
                PackCostPage {
                    navController.navigate("firstMonthWithoutSmokingPage")
                }
            }
            composable(route = "firstMonthWithoutSmokingPage") {
                FirstMonthWithoutSmokingPage {
                    navController.navigate("notificationsPage")
                }
            }
            composable(route = "notificationsPage") {
                NotificationsPage {
                    navController.navigate("mainScreen")
                }
            }
            composable(route = "mainScreen") {
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

fun forwardTransitionAnimation(): EnterTransition {
    return fadeIn(
        animationSpec = tween(300, easing = LinearEasing)
    ) + slideInHorizontally(
        animationSpec = tween(300, easing = EaseIn),
        initialOffsetX = { fullWidth -> fullWidth / 2 }
    )
}

fun backwardTransitionAnimation(): ExitTransition {
    return fadeOut(
        animationSpec = tween(300, easing = LinearEasing)
    ) + slideOutHorizontally(
        animationSpec = tween(300, easing = EaseOut),
        targetOffsetX = { fullWidth -> -fullWidth / 2 }
    )
}

// Анимация для popEnterTransition
fun popForwardTransitionAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(300, easing = LinearEasing)) +
            slideInHorizontally(
                animationSpec = tween(300, easing = EaseIn),
                initialOffsetX = { fullWidth -> -fullWidth }
            )
}

// Анимация для popExitTransition
fun popBackwardTransitionAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(300, easing = LinearEasing)) +
            slideOutHorizontally(
                animationSpec = tween(300, easing = EaseOut),
                targetOffsetX = { fullWidth -> fullWidth }
            )
}