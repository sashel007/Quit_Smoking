package ru.sashel007.quitsmoking

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint
import ru.sashel007.quitsmoking.data.db.dao.AchievementDao
import ru.sashel007.quitsmoking.data.db.entity.AchievementData
import ru.sashel007.quitsmoking.mainscreen.MainScreen
import ru.sashel007.quitsmoking.mainscreen.elements.AppNavigator
import ru.sashel007.quitsmoking.mainscreen.elements.AppSettings
import ru.sashel007.quitsmoking.mainscreen.elements.settings.CancellingTimeChoosing
import ru.sashel007.quitsmoking.navigator.CigarettesInPackPage
import ru.sashel007.quitsmoking.navigator.CigarettesPerDayPage
import ru.sashel007.quitsmoking.navigator.FirstMonthWithoutSmokingPage
import ru.sashel007.quitsmoking.navigator.NotificationsPage
import ru.sashel007.quitsmoking.navigator.PackCostPage
import ru.sashel007.quitsmoking.navigator.PageIndicator
import ru.sashel007.quitsmoking.navigator.QuitDateSelectionPage
import ru.sashel007.quitsmoking.navigator.StartingPage
import ru.sashel007.quitsmoking.ui.theme.QuitSmokingTheme
import ru.sashel007.quitsmoking.util.AppState
import ru.sashel007.quitsmoking.util.MySharedPref
import ru.sashel007.quitsmoking.viewmodel.AchievementViewModel
import ru.sashel007.quitsmoking.viewmodel.FirstLaunchViewModel
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel
import ru.sashel007.quitsmoking.viewmodel.UserViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var sharedPref: MySharedPref

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        val userViewModel: UserViewModel by viewModels<UserViewModel>()
        val firstLaunchViewModel: FirstLaunchViewModel by viewModels<FirstLaunchViewModel>()
        val smokingStatsViewModel: SmokingStatsViewModel by viewModels<SmokingStatsViewModel>()
        val achievementViewModel: AchievementViewModel by viewModels<AchievementViewModel>()

        setContent {
            QuitSmokingTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    AppNavigator(
                        userViewModel,
                        firstLaunchViewModel,
                        smokingStatsViewModel,
                        achievementViewModel
                    )
                }
            }
        }

        smokingStatsViewModel.showDialogEvent.observe(this, Observer { event ->
            event.getContentIfNotHandled()
            onShowDialog()
        })
    }

    private fun onShowDialog() {
        Toast.makeText(applicationContext, "TIME IN FUTURE", Toast.LENGTH_SHORT).show()
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun AppNavigator(
//    userViewModel: UserViewModel,
//    firstLaunchViewModel: FirstLaunchViewModel,
//    smokingStatsViewModel: SmokingStatsViewModel,
//    achievementViewModel: AchievementViewModel
//) {
//    val navController = rememberNavController()
//    var currentPage by remember { mutableIntStateOf(0) }
//    val appState by firstLaunchViewModel.launchState.collectAsState()
//
//    DisposableEffect(navController) {
//        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
//            when (destination.route) {
//                "startingPage" -> currentPage = 0
//                "quitDateSelectionPage" -> currentPage = 1
//                "cigarettesPerDayPage" -> currentPage = 2
//                "cigarettesInPackPage" -> currentPage = 3
//                "packCostPage" -> currentPage = 4
//                "firstMonthWithoutSmokingPage" -> currentPage = 5
//                "notificationsPage" -> currentPage = 6
//                "mainScreen" -> currentPage = 7
//            }
//        }
//        navController.addOnDestinationChangedListener(listener)
//        onDispose {
//            navController.removeOnDestinationChangedListener(listener)
//        }
//    }
//
//    LaunchedEffect(appState) {
//        when (appState) {
//            AppState.FIRST_LAUNCH -> {
//                navController.navigate("startingPage") {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                }
//            }
//
//            AppState.SUBSEQUENT_LAUNCH -> {
//                navController.navigate("mainScreen") {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                }
//            }
//
//            else -> Unit
//        }
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        NavHost(
//            navController = navController,
//            startDestination = "startingPage",
//            enterTransition = { forwardTransitionAnimation() },
//            exitTransition = { backwardTransitionAnimation() },
//            popEnterTransition = { popForwardTransitionAnimation() },
//            popExitTransition = { popBackwardTransitionAnimation() }
//        ) {
//            composable(route = "startingPage") {
//                StartingPage {
//                    navController.navigate("quitDateSelectionPage")
//                }
//            }
//            composable(route = "quitDateSelectionPage") {
//                QuitDateSelectionPage(
//                    userViewModel = userViewModel,
//                    navController = navController
//                ) {
//                    navController.navigate("cigarettesPerDayPage")
//                }
//            }
//            composable(route = "cigarettesPerDayPage") {
//                CigarettesPerDayPage(navController = navController, userViewModel = userViewModel) {
//                    navController.navigate("cigarettesInPackPage")
//                }
//            }
//            composable(route = "cigarettesInPackPage") {
//                CigarettesInPackPage(navController = navController, userViewModel = userViewModel) {
//                    navController.navigate("packCostPage")
//                }
//            }
//            composable(route = "packCostPage") {
//                PackCostPage(navController = navController, userViewModel = userViewModel) {
//                    navController.navigate("firstMonthWithoutSmokingPage")
//                }
//            }
//            composable(route = "firstMonthWithoutSmokingPage") {
//                FirstMonthWithoutSmokingPage(
//                    navController = navController,
//                    userViewModel = userViewModel
//                ) {
//                    navController.navigate("notificationsPage")
//                }
//            }
//            composable(route = "notificationsPage") {
//                NotificationsPage {
//                    navController.navigate("mainScreen")
//                }
//            }
//            composable(route = "mainScreen") {
//                MainScreen(
//                    smokingStatsViewModel = smokingStatsViewModel,
//                    achievementViewModel = achievementViewModel,
//                    navController = navController
//                )
//            }
//            composable(route = "settings") {
//                AppSettings(navController = navController)
//            }
//            composable(route = "settings_cancellingsmoking") {
//                CancellingTimeChoosing(userViewModel = userViewModel, navController = navController)
//            }
//        }
//        if (currentPage != 7) {
//            PageIndicator(
//                currentPage = currentPage,
//                numberOfPages = 7,
//                Modifier.align(Alignment.BottomCenter)
//            )
//        }
//    }
//
//}

