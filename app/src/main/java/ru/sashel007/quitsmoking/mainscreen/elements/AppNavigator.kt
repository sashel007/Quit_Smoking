package ru.sashel007.quitsmoking.mainscreen.elements

import android.os.Build
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.mainscreen.MainScreen
import ru.sashel007.quitsmoking.navigator.CigarettesInPackPage
import ru.sashel007.quitsmoking.navigator.CigarettesPerDayPage
import ru.sashel007.quitsmoking.navigator.FirstMonthWithoutSmokingPage
import ru.sashel007.quitsmoking.navigator.NotificationsPage
import ru.sashel007.quitsmoking.navigator.PackCostPage
import ru.sashel007.quitsmoking.navigator.QuitDateSelectionPage
import ru.sashel007.quitsmoking.navigator.StartingPage
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.util.AppState
import ru.sashel007.quitsmoking.viewmodel.AchievementViewModel
import ru.sashel007.quitsmoking.viewmodel.FirstLaunchViewModel
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(
    userViewModel: UserViewModel,
    firstLaunchViewModel: FirstLaunchViewModel,
    smokingStatsViewModel: SmokingStatsViewModel,
    achievementViewModel: AchievementViewModel,
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var currentPage by remember { mutableStateOf("mainScreen") }
    val appState by firstLaunchViewModel.launchState.collectAsState()
    val achievements: List<AchievementDto>? = achievementViewModel.achievements.value

    val showNavigationBar = remember(currentRoute) {
        mutableStateOf(
            currentRoute in listOf(
                "mainScreen", "motivation", "tips", "settings"
            )
        )
    }

    LaunchedEffect(appState) {
        when (appState) {
            AppState.FIRST_LAUNCH -> {
                navController.navigate("startingPage") {
                    popUpTo("startingPage") { inclusive = true }
                }
            }

            AppState.SUBSEQUENT_LAUNCH -> {
                navController.navigate("mainScreen") {
                    popUpTo("mainScreen") { inclusive = true }
                }
            }

            else -> Unit
        }
    }


    LaunchedEffect(currentRoute) {
        showNavigationBar.value = currentRoute in listOf(
            "mainScreen", "motivation", "tips", "settings"
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = if (appState == AppState.FIRST_LAUNCH) "startingPage" else "mainScreen",
            enterTransition = { forwardTransitionAnimation() },
            exitTransition = { backwardTransitionAnimation() },
            popEnterTransition = { popForwardTransitionAnimation() },
            popExitTransition = { popBackwardTransitionAnimation() }
        ) {
            composable("startingPage") { StartingPage { navController.navigate("quitDateSelectionPage") } }
            composable("quitDateSelectionPage") {
                QuitDateSelectionPage(
                    userViewModel, navController
                ) { navController.navigate("cigarettesPerDayPage") }
            }
            composable("cigarettesPerDayPage") {
                CigarettesPerDayPage(
                    navController, userViewModel
                ) { navController.navigate("cigarettesInPackPage") }
            }
            composable("cigarettesInPackPage") {
                CigarettesInPackPage(
                    navController, userViewModel
                ) { navController.navigate("packCostPage") }
            }
            composable("packCostPage") {
                PackCostPage(
                    navController, userViewModel
                ) { navController.navigate("firstMonthWithoutSmokingPage") }
            }
            composable("firstMonthWithoutSmokingPage") {
                FirstMonthWithoutSmokingPage(
                    navController, userViewModel
                ) { navController.navigate("notificationsPage") }
            }
            composable("notificationsPage") { NotificationsPage { navController.navigate("mainScreen") } }
            composable("mainScreen") {
                MainScreen(
                    smokingStatsViewModel,
                    achievementViewModel,
                    navController
                )
            }
            composable("motivation") { MotivationScreen(smokingStatsViewModel, navController) }
            composable("tips") { TipsScreen() }
            composable("settings") { AppSettings(navController) }
            composable("achievements_list") {
                AchievementsList(navController, achievements)
            }
            composable("breath_greetings") { BreathGreetingScreen(navController) }
            composable("breath_practice") { BreathPractice(navController) }
        }

        if (showNavigationBar.value) {
            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White)
                    .height(68.dp)
                    .shadow(2.dp, RectangleShape), contentColor = Color.White
            ) {
                NavigationBarItem(icon = {
                    Icon(
                        painterResource(id = R.drawable.nav_bar_mainscreen_icon),
                        contentDescription = null
                    )
                }, selected = currentPage == "mainScreen", onClick = {
                    if (currentRoute != "mainScreen") {
                        navController.navigate("mainScreen") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    currentPage = "mainScreen"
                }, modifier = Modifier.size(22.dp), label = {
                    Text(
                        text = "Статистика",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.ExtraLight
                    )
                })
                NavigationBarItem(icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.nav_bar_motivation_icon),
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                }, selected = currentPage == "motivation", onClick = {
                    if (currentRoute != "motivation") {
                        navController.navigate("motivation") {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                    currentPage = "motivation"
                }, label = {
                    Text(
                        text = "Мотивация",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.ExtraLight
                    )
                })
                NavigationBarItem(icon = { Icon(Icons.Filled.Info, contentDescription = null) },
                    selected = currentPage == "tips",
                    onClick = {
                        if (currentRoute != "tips") {
                            navController.navigate("tips") {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        currentPage = "tips"
                    },
                    label = {
                        Text(
                            text = "Советы",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.ExtraLight
                        )
                    })
                NavigationBarItem(icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
                    selected = currentPage == "settings",
                    onClick = {
                        if (currentRoute != "settings") {
                            navController.navigate("settings") {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        currentPage = "settings"
                    },
                    label = {
                        Text(
                            text = "Настройки",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.ExtraLight
                        )
                    })
            }
        }
    }
}

fun backwardTransitionAnimation(): ExitTransition {
    return fadeOut(
        animationSpec = tween(300, easing = LinearEasing)
    ) + slideOutHorizontally(animationSpec = tween(300, easing = EaseOut),
        targetOffsetX = { fullWidth -> -fullWidth / 2 })
}

fun popForwardTransitionAnimation(): EnterTransition {
    return fadeIn(animationSpec = tween(300, easing = LinearEasing)) + slideInHorizontally(
        animationSpec = tween(300, easing = EaseIn),
        initialOffsetX = { fullWidth -> -fullWidth })
}

fun popBackwardTransitionAnimation(): ExitTransition {
    return fadeOut(animationSpec = tween(300, easing = LinearEasing)) + slideOutHorizontally(
        animationSpec = tween(300, easing = EaseOut),
        targetOffsetX = { fullWidth -> fullWidth })
}

fun forwardTransitionAnimation(): EnterTransition {
    return fadeIn(
        animationSpec = tween(300, easing = LinearEasing)
    ) + slideInHorizontally(animationSpec = tween(300, easing = EaseIn),
        initialOffsetX = { fullWidth -> fullWidth / 2 })
}