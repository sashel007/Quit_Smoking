package ru.sashel007.quitsmoking.mainscreen.elements

import android.os.Build
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.mainscreen.MainScreen
import ru.sashel007.quitsmoking.mainscreen.elements.settings.CancellingTimeChoosing
import ru.sashel007.quitsmoking.mainscreen.elements.settings.MailToDevInfoPage
import ru.sashel007.quitsmoking.mainscreen.elements.settings.PolicyPage
import ru.sashel007.quitsmoking.mainscreen.elements.settings.UserSmokingDataChoosing
import ru.sashel007.quitsmoking.navigator.CigarettesInPackPage
import ru.sashel007.quitsmoking.navigator.CigarettesPerDayPage
import ru.sashel007.quitsmoking.navigator.FirstMonthWithoutSmokingPage
import ru.sashel007.quitsmoking.navigator.NotificationsPage
import ru.sashel007.quitsmoking.navigator.PackCostPage
import ru.sashel007.quitsmoking.navigator.QuitDateSelectionPage
import ru.sashel007.quitsmoking.navigator.StartingPage
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.util.AppState
import ru.sashel007.quitsmoking.util.MySharedPref
import ru.sashel007.quitsmoking.viewmodel.AchievementViewModel
import ru.sashel007.quitsmoking.viewmodel.LastOpenedPageViewModel
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

object NavRoutes {
    const val MAIN_SCREEN = "mainScreen"
    const val STARTING_PAGE = "startingPage"
    const val MOTIVATION = "motivation"
    const val TIPS = "tips"
    const val SETTINGS = "settings"
    const val QUIT_DATE_SELECTION_PAGE = "quitDateSelectionPage"
    const val CIGARETTES_PER_DAY_PAGE = "cigarettesPerDayPage"
    const val CIGARETTES_IN_PACK_PAGE = "cigarettesInPackPage"
    const val PACK_COST_PAGE = "packCostPage"
    const val FIRST_MONTH_WITHOUT_SMOKING_PAGE = "firstMonthWithoutSmokingPage"
    const val NOTIFICATIONS_PAGE = "notificationsPage"
    const val ACHIEVEMENTS_LIST = "achievements_list"
    const val BREATH_GREETINGS = "breath_greetings"
    const val BREATH_PRACTICE = "breath_practice"
    const val TIPS_LIST = "tips_list"
    const val FACTS_LIST = "facts_list"
    const val MYTHS_LIST = "myths_list"
    const val SETTINGS_CANCELLING_SMOKING = "settings_cancellingsmoking"
    const val SETTINGS_USER_DATA = "settings_userdata"
    const val MAIL_TO_DEV_INFO_PAGE = "mail_to_dev_info_page"
    const val SETTINGS_POLICY_PAGE = "settings_policy_page"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigator(
    userViewModel: UserViewModel,
    firstLaunchViewModel: LastOpenedPageViewModel,
    smokingStatsViewModel: SmokingStatsViewModel,
    achievementViewModel: AchievementViewModel,
    sharedPref: MySharedPref
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var currentPage by remember { mutableStateOf(NavRoutes.MAIN_SCREEN) }
    val appState by firstLaunchViewModel.launchState.collectAsState()
    val achievements: List<AchievementDto>? = achievementViewModel.achievements.value
    val showNavigationBar = remember(currentRoute) {
        mutableStateOf(
            currentRoute in listOf(
                NavRoutes.MAIN_SCREEN, NavRoutes.MOTIVATION, NavRoutes.TIPS, NavRoutes.SETTINGS
            )
        )
    }

    LaunchedEffect(appState) {
        when (appState) {
            AppState.STARTING_PAGE -> {
                navController.navigate(NavRoutes.STARTING_PAGE) {
                    popUpTo(NavRoutes.STARTING_PAGE) { inclusive = true }
                }
            }

            AppState.QUIT_DATE_SELECTION_PAGE -> {
                navController.navigate(NavRoutes.QUIT_DATE_SELECTION_PAGE)
            }

            AppState.CIGARETTES_PER_DAY_PAGE -> {
                navController.navigate(NavRoutes.CIGARETTES_PER_DAY_PAGE)
            }

            AppState.CIGARETTES_IN_PACK_PAGE -> {
                navController.navigate(NavRoutes.CIGARETTES_IN_PACK_PAGE)
            }

            AppState.PACK_COST_PAGE -> {
                navController.navigate(NavRoutes.PACK_COST_PAGE)
            }

            AppState.FIRST_MONTH_WITHOUT_SMOKING -> {
                navController.navigate(NavRoutes.FIRST_MONTH_WITHOUT_SMOKING_PAGE)
            }

            AppState.NOTIFICATION_PAGE -> {
                navController.navigate(NavRoutes.NOTIFICATIONS_PAGE)
            }

            AppState.SUBSEQUENT_LAUNCH -> {
                navController.navigate(NavRoutes.MAIN_SCREEN) {
                    popUpTo(NavRoutes.MAIN_SCREEN) { inclusive = true }
                }
            }
        }
    }

    LaunchedEffect(currentRoute) {
        showNavigationBar.value = currentRoute in listOf(
            NavRoutes.MAIN_SCREEN, NavRoutes.MOTIVATION, NavRoutes.TIPS, NavRoutes.SETTINGS
        )
    }

//    PageIndicator(currentPage = 2, numberOfPages = 6)

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController,
            startDestination = when (appState) {
                AppState.STARTING_PAGE -> NavRoutes.STARTING_PAGE
                AppState.QUIT_DATE_SELECTION_PAGE -> NavRoutes.QUIT_DATE_SELECTION_PAGE
                AppState.CIGARETTES_PER_DAY_PAGE -> NavRoutes.CIGARETTES_PER_DAY_PAGE
                AppState.CIGARETTES_IN_PACK_PAGE -> NavRoutes.CIGARETTES_IN_PACK_PAGE
                AppState.PACK_COST_PAGE -> NavRoutes.PACK_COST_PAGE
                AppState.FIRST_MONTH_WITHOUT_SMOKING -> NavRoutes.FIRST_MONTH_WITHOUT_SMOKING_PAGE
                AppState.NOTIFICATION_PAGE -> NavRoutes.NOTIFICATIONS_PAGE
                AppState.SUBSEQUENT_LAUNCH -> NavRoutes.MAIN_SCREEN
                else -> NavRoutes.STARTING_PAGE
            },
            enterTransition = { forwardTransitionAnimation() },
            exitTransition = { backwardTransitionAnimation() },
            popEnterTransition = { popForwardTransitionAnimation() },
            popExitTransition = { popBackwardTransitionAnimation() }) {
            composable(NavRoutes.STARTING_PAGE) {
                StartingPage {
                    sharedPref.setLastOpenedPage(NavRoutes.STARTING_PAGE)
                    navController.navigate(NavRoutes.QUIT_DATE_SELECTION_PAGE)
                }
            }
            composable(NavRoutes.QUIT_DATE_SELECTION_PAGE) {
                QuitDateSelectionPage(userViewModel, navController) {
                    sharedPref.setLastOpenedPage(NavRoutes.QUIT_DATE_SELECTION_PAGE)
                    navController.navigate(NavRoutes.CIGARETTES_PER_DAY_PAGE)
                }
            }
            composable(NavRoutes.CIGARETTES_PER_DAY_PAGE) {
                CigarettesPerDayPage(navController, userViewModel) {
                    sharedPref.setLastOpenedPage(NavRoutes.CIGARETTES_PER_DAY_PAGE)
                    navController.navigate(NavRoutes.CIGARETTES_IN_PACK_PAGE)
                }
            }
            composable(NavRoutes.CIGARETTES_IN_PACK_PAGE) {
                CigarettesInPackPage(navController, userViewModel) {
                    sharedPref.setLastOpenedPage(NavRoutes.CIGARETTES_IN_PACK_PAGE)
                    navController.navigate(NavRoutes.PACK_COST_PAGE)
                }
            }
            composable(NavRoutes.PACK_COST_PAGE) {
                PackCostPage(navController, userViewModel) {
                    sharedPref.setLastOpenedPage(NavRoutes.PACK_COST_PAGE)
                    navController.navigate(NavRoutes.FIRST_MONTH_WITHOUT_SMOKING_PAGE)
                }
            }
            composable(NavRoutes.FIRST_MONTH_WITHOUT_SMOKING_PAGE) {
                FirstMonthWithoutSmokingPage(navController, userViewModel) {
                    sharedPref.setLastOpenedPage(NavRoutes.FIRST_MONTH_WITHOUT_SMOKING_PAGE)
                    navController.navigate(NavRoutes.NOTIFICATIONS_PAGE)
                }
            }
            composable(NavRoutes.NOTIFICATIONS_PAGE) {
                NotificationsPage {
                    sharedPref.setLastOpenedPage(NavRoutes.NOTIFICATIONS_PAGE)
                    navController.navigate(NavRoutes.MAIN_SCREEN)
                }
            }
            composable(NavRoutes.MAIN_SCREEN) {
                sharedPref.setLastOpenedPage(NavRoutes.MAIN_SCREEN)
                MainScreen(smokingStatsViewModel, achievementViewModel, navController)
            }
            composable(NavRoutes.MOTIVATION) {
                MotivationScreen(
                    smokingStatsViewModel, navController
                )
            }
            composable(NavRoutes.TIPS) { TipsScreen(navController) }
            composable(NavRoutes.SETTINGS) { AppSettings(navController) }
            composable(NavRoutes.ACHIEVEMENTS_LIST) {
                AchievementsList(
                    navController, achievements
                )
            }
            composable(NavRoutes.BREATH_GREETINGS) { BreathGreetingScreen(navController) }
            composable(NavRoutes.BREATH_PRACTICE) { BreathPractice(navController) }
            composable(NavRoutes.TIPS_LIST) { TipsList(navController) }
            composable(NavRoutes.FACTS_LIST) { FactList(navController) }
            composable(NavRoutes.MYTHS_LIST) { MythList(navController) }
            composable(NavRoutes.SETTINGS_CANCELLING_SMOKING) {
                CancellingTimeChoosing(userViewModel, navController)
            }
            composable(NavRoutes.SETTINGS_USER_DATA) {
                UserSmokingDataChoosing(navController, userViewModel)
            }
            composable(NavRoutes.MAIL_TO_DEV_INFO_PAGE) { MailToDevInfoPage(navController) }
            composable(NavRoutes.SETTINGS_POLICY_PAGE) { PolicyPage(navController)}
        }
        if (showNavigationBar.value) {
            NavigationBar(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(Color.White).height(68.dp)
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