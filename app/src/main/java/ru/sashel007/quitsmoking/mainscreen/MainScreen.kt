package ru.sashel007.quitsmoking.mainscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.mainscreen.elements.Achievements
import ru.sashel007.quitsmoking.mainscreen.elements.Advices
import ru.sashel007.quitsmoking.mainscreen.elements.LicencedText
import ru.sashel007.quitsmoking.mainscreen.elements.ModalLayer
import ru.sashel007.quitsmoking.mainscreen.elements.Motivation
import ru.sashel007.quitsmoking.mainscreen.elements.MyAppBar
import ru.sashel007.quitsmoking.mainscreen.elements.ProgressLine
import ru.sashel007.quitsmoking.mainscreen.elements.Timer
import ru.sashel007.quitsmoking.viewmodel.AchievementViewModel
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(
    userViewModel: UserViewModel,
    smokingStatsViewModel: SmokingStatsViewModel,
    achievementViewModel: AchievementViewModel,
    navController: NavController
) {
    val smokingStats = smokingStatsViewModel.smokingStats.observeAsState()
    val scrollState = rememberLazyListState()
    val achievements = achievementViewModel.achievements.observeAsState()
    var showDetail by remember { mutableStateOf<AchievementDto?>(null) }
    val onDismiss = { showDetail = null }
    val imageSize = 80.dp

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
                                        Color(0xFFEDB9FF), // верхний цвет - #EDB9FF
                                        Color(0xFF7FBDF6).copy(alpha = 0.8f)  // нижний цвет - #7FBDF6 с прозрачностью 80%
                                    )
                                )
                            )
                    ) {
                        MyAppBar(navController = navController)
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
                            .align(Alignment.BottomCenter)
                    )
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

                    Achievements(achievements, onAchievementClick = { achievement ->
                        showDetail = achievement
                    })
                    Spacer(modifier = Modifier.height(26.dp))
                    Motivation()
                    Spacer(modifier = Modifier.height(26.dp))
                    Advices()
                    Spacer(modifier = Modifier.height(8.dp))
                    LicencedText(8.sp, 10.sp, Modifier)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showDetail != null) {
        ModalLayer(achievement = showDetail!!, onDismiss = onDismiss, imageSize)
    }
}

