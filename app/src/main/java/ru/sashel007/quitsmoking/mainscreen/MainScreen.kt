package ru.sashel007.quitsmoking.mainscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.mainscreen.elements.Achievements
import ru.sashel007.quitsmoking.mainscreen.elements.BreathScreen
import ru.sashel007.quitsmoking.mainscreen.elements.DailyTip
import ru.sashel007.quitsmoking.mainscreen.elements.ModalLayer
import ru.sashel007.quitsmoking.mainscreen.elements.MyAppBar
import ru.sashel007.quitsmoking.mainscreen.elements.ProgressLine
import ru.sashel007.quitsmoking.mainscreen.elements.Timer
import ru.sashel007.quitsmoking.mainscreen.elements.YourMood
import ru.sashel007.quitsmoking.viewmodel.AchievementViewModel
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    smokingStatsViewModel: SmokingStatsViewModel,
    achievementViewModel: AchievementViewModel,
    navController: NavController
) {
    val smokingStats = smokingStatsViewModel.smokingStats.observeAsState()
    val scrollState = rememberLazyListState()
    val achievements = achievementViewModel.achievements.observeAsState()

    var startAnimation by remember { mutableStateOf(false) }
    val imageSize = 120.dp
    var showDetail by remember { mutableStateOf<AchievementDto?>(null) }
    val onDismiss = { showDetail = null }

    LazyColumn(state = scrollState) {
        item {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                /** Область с таймером */

                Box {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFEDB9FF),
                                        Color(0xFF7FBDF6).copy(alpha = 0.8f)
                                    )
                                )
                            )
                    ) {
                        MyAppBar()
                        Spacer(Modifier.height(48.dp))
                        Timer(smokingStats)
                        Spacer(modifier = Modifier.height(20.dp))
                        ProgressLine(smokingStats)
                        Spacer(modifier = Modifier.height(36.dp))
                    }
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(32.dp, 32.dp))
                            .background(Color.White)
                            .align(Alignment.BottomCenter),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(width = 40.dp, height = 5.dp)
                                .background(Color.LightGray, shape = RoundedCornerShape(50))
                        )
                    }
                }
            }
        }

        item {
            /** Область с белым фоном */

            Box(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clip(RoundedCornerShape(26.dp, 26.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Achievements(
                        achievementsState = achievements,
                        onAchievementClick = { achievement ->
                            showDetail = achievement
                        },
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        YourMood()
                        DailyTip()
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    BreathScreen(navController = navController)
                    Spacer(modifier = Modifier.height(100.dp))

                }
            }
        }
    }

    if (showDetail != null) {
        ModalLayer(
            achievement = showDetail!!,
            startAnimation = startAnimation,
            onDismiss = onDismiss,
            imageSize
        )
    }


}

