package ru.sashel007.quitsmoking.mainscreen.elements

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MotivationScreen(statsViewModel: SmokingStatsViewModel, navController: NavController) {
    val daysAfterCancelling = statsViewModel.smokingStats.value?.days ?: 0
    val hoursAfterCancelling = statsViewModel.smokingStats.value?.hours ?: 0

    val scaleFormat = when {
        daysAfterCancelling <= 7 -> ScaleFormat.DAYS
        daysAfterCancelling <= 28 -> ScaleFormat.WEEKS
        else -> ScaleFormat.MONTHS
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(/* Color(0xFFFBFAFA) */ Color.LightGray)
    ) {
        Column {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp))
                    .clip(RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp))
                    .background(Color.White)
                    .padding(14.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Мотивация",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp,
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp)
                    .wrapContentHeight()
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .background(Color.White, RoundedCornerShape(20.dp))
            ) {
                Column {
                    Text(
                        "Трекер воздержания за ${scaleFormat.label}:",
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = scaleFormat.subLabel,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(start = 16.dp)
                            .graphicsLayer {
                                translationY = 50f
                            },
                        fontSize = 12.sp
                    )
                    Row {
                        ProgressTracker(daysAfterCancelling, hoursAfterCancelling, scaleFormat)
                        Image(
                            painter = painterResource(id = R.drawable.achievement_1),
                            contentDescription = "",
                            modifier = Modifier.size(60.dp)
                        )
                    }
                    Text(
                        "Для поддержания духа в период воздержания рекомендуем периодически следить за прогрессом.",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        lineHeight = 18.sp,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    Spacer(Modifier.size(16.dp))
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .wrapContentHeight()
                    .shadow(2.dp, RoundedCornerShape(20.dp))
                    .background(Color.White, RoundedCornerShape(20.dp))
            ) {
                Column {
                    val colorStops = arrayOf(
                        0.0f to Color(0xFFCDFCC5),
                        0.5f to Color(0xFF86B5FF),
                        1.0f to Color(0xFF364AFF)
                    )
                    Box(modifier = Modifier
                        .height(160.dp)
                        .fillMaxWidth()
                        .padding(14.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { navController.navigate("achievements_list") }
                        .background(Brush.linearGradient(colorStops = colorStops))

                    ) {
                        Text(
                            "Ваши достижения",
                            color = Color.DarkGray,
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.achievement_medal_img),
                            contentDescription = "Ваши достижения",
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.BottomEnd)
                                .graphicsLayer {
                                    translationY = 16.dp.toPx()
                                    translationX = 16.dp.toPx()
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }
}

enum class ScaleFormat(val label: String, val subLabel: String, val totalPeriods: Int) {
    DAYS("неделю", "Дни", 7),
    WEEKS("месяц", "Недели", 4),
    MONTHS("год", "Месяцы", 12)
}

@Composable
fun ProgressTracker(days: Long, hours: Long, scaleFormat: ScaleFormat) {
    val totalHours = when (scaleFormat) {
        ScaleFormat.DAYS -> scaleFormat.totalPeriods * 24
        ScaleFormat.WEEKS -> scaleFormat.totalPeriods * 7 * 24
        ScaleFormat.MONTHS -> scaleFormat.totalPeriods * 30 * 24
    }

    val progress = (days * 24 + hours).toFloat() / totalHours
    val periods = (0 until scaleFormat.totalPeriods).toList()

    Box(
        modifier = Modifier
            .padding(16.dp)
            .height(40.dp)
            .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(40.dp)
                .background(Color.Green, RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            periods.forEach { period ->
                Box(
                    modifier = Modifier
                        .weight(if (period == periods.last()) 1.01f else 1f)
                        .height(40.dp)
                ) {
                    Text(
                        text = "${period + 1}",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(horizontal = 4.dp),
                        color = Color.Gray,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Light
                    )
                    if (period < periods.last()) {
                        Box(
                            modifier = Modifier
                                .width(1.dp)
                                .height(40.dp)
                                .background(Color.Gray)
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            }
        }
    }
}



//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//fun MotivationScreen(statsViewModel: SmokingStatsViewModel, navController: NavController) {
//    val daysAfterCancelling = statsViewModel.smokingStats.value?.days ?: 0
//    val hoursAfterCancelling = statsViewModel.smokingStats.value?.hours ?: 0
//    val totalDays = calculateProgressPeriod(daysAfterCancelling.toInt())
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(/* Color(0xFFFBFAFA) */ Color.LightGray)
//    ) {
//        Column {
//            Box(
//                contentAlignment = Alignment.Center,
//                modifier = Modifier
//                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp))
//                    .clip(RoundedCornerShape(0.dp, 0.dp, 12.dp, 12.dp))
//                    .background(Color.White)
//                    .padding(14.dp)
//                    .fillMaxWidth()
//            ) {
//                Text(
//                    text = "Мотивация",
//                    fontFamily = MyTextStyles.mRobotoFontFamily,
//                    fontWeight = FontWeight.Medium,
//                    fontSize = 20.sp,
//                )
//            }
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 30.dp)
//                    .wrapContentHeight()
//                    .shadow(2.dp, RoundedCornerShape(20.dp))
//                    .background(Color.White, RoundedCornerShape(20.dp))
//            ) {
//                Column {
//                    Text(
//                        "Трекер воздержания за ${if (totalDays == 7) "неделю:" else "месяц:"}",
//                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
//                        fontFamily = MyTextStyles.mRobotoFontFamily,
//                        fontWeight = FontWeight.Normal
//                    )
//                    Text(
//                        text = if (totalDays == 7) "Дни" else "Недели",
//                        fontFamily = MyTextStyles.mRobotoFontFamily,
//                        fontWeight = FontWeight.Normal,
//                        color = Color.DarkGray,
//                        modifier = Modifier
//                            .padding(start = 16.dp)
//                            .graphicsLayer {
//                                translationY = 50f
//                            },
//                        fontSize = 12.sp
//                    )
//                    Row() {
//                        ProgressTracker(daysAfterCancelling, hoursAfterCancelling, totalDays)
//                        Image(
//                            painter = painterResource(id = R.drawable.achievement_1),
//                            contentDescription = "",
//                            modifier = Modifier.size(60.dp)
//                        )
//                    }
//                    Text(
//                        "Для поддержания духа в период воздержания рекомендуем периодически следить за прогрессом.",
//                        fontFamily = MyTextStyles.mRobotoFontFamily,
//                        fontWeight = FontWeight.Light,
//                        lineHeight = 18.sp,
//                        fontSize = 14.sp,
//                        color = Color.Gray,
//                        modifier = Modifier.padding(horizontal = 16.dp)
//                    )
//                    Spacer(Modifier.size(16.dp))
//                }
//            }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp)
//                    .wrapContentHeight()
//                    .shadow(2.dp, RoundedCornerShape(20.dp))
//                    .background(Color.White, RoundedCornerShape(20.dp))
//            ) {
//                Column {
//                    val colorStops = arrayOf(
//                        0.0f to Color(0xFFCDFCC5),
//                        0.5f to Color(0xFF86B5FF),
//                        1.0f to Color(0xFF364AFF)
//                    )
//                    Box(modifier = Modifier
//                        .height(160.dp)
//                        .fillMaxWidth()
//                        .padding(14.dp)
//                        .clip(RoundedCornerShape(16.dp))
//                        .clickable { navController.navigate("achievements_list") }
//                        .background(Brush.linearGradient(colorStops = colorStops))
//
//                    ) {
//                        Text(
//                            "Ваши достижения",
//                            color = Color.DarkGray,
//                            fontFamily = MyTextStyles.mRobotoFontFamily,
//                            fontWeight = FontWeight.SemiBold,
//                            fontSize = 20.sp,
//                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
//                        )
//                        Image(
//                            painter = painterResource(id = R.drawable.achievement_medal_img),
//                            contentDescription = "Ваши достижения",
//                            modifier = Modifier
//                                .size(120.dp)
//                                .align(Alignment.BottomEnd)
//                                .graphicsLayer {
//                                    translationY = 16.dp.toPx()
//                                    translationX = 16.dp.toPx()
//                                },
//                            contentScale = ContentScale.Crop
//                        )
//                    }
//                }
//            }
//
//        }
//    }
//}
//
//@Composable
//fun ProgressTracker(days: Long, hours: Long, totalDays: Int = 7) {
//    val progress = (days * 24 + hours).toFloat() / (totalDays * 24)
//    val dayMarks = (0 until totalDays).toList()
//
//    Box(
//        modifier = Modifier
//            .padding(16.dp)
//            .height(40.dp)
//            .background(Color.Gray.copy(alpha = 0.2f), RoundedCornerShape(20.dp))
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth(progress)
//                .height(40.dp)
//                .background(Color.Green, RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp))
//        )
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(40.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            dayMarks.forEach { day ->
//                Box(
//                    modifier = Modifier
//                        .weight(if (day == dayMarks.last()) 1.01f else 1f)
//                        .height(40.dp)
//                ) {
//                    Text(
//                        text = "${day + 1}",
//                        modifier = Modifier
//                            .align(Alignment.Center)
//                            .padding(horizontal = 4.dp),
//                        color = Color.Gray,
//                        fontFamily = MyTextStyles.mRobotoFontFamily,
//                        fontWeight = FontWeight.Light
//                    )
//                    if (day < dayMarks.last()) {
//                        Box(
//                            modifier = Modifier
//                                .width(1.dp)
//                                .height(40.dp)
//                                .background(Color.Gray)
//                                .align(Alignment.CenterEnd)
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//
//fun calculateProgressPeriod(days: Int): Int {
//    return if (days <= 28) 7 else 28  // Пример простой логики переключения
//}