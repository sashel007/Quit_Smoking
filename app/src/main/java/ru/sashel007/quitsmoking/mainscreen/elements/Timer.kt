package ru.sashel007.quitsmoking.mainscreen.elements

import android.util.Log
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.data.repository.dto.SmokingStats
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun Timer(smokingStats: androidx.compose.runtime.State<SmokingStats?>) {
    val totalDays by remember(smokingStats.value?.days) {
        derivedStateOf {
            smokingStats.value?.days ?: 0L
        }
    }
    val hours by remember(smokingStats.value?.hours) {
        derivedStateOf {
            smokingStats.value?.hours ?: 0L
        }
    }
    val minutes by remember(smokingStats.value?.minutes) {
        derivedStateOf {
            smokingStats.value?.minutes ?: 0L
        }
    }

    val years = totalDays / 365
    val remainingDays = totalDays % 365
    val months = remainingDays / 30
    val days = remainingDays % 30

    if (smokingStats.value == null || smokingStats.value == SmokingStats(0, 0, 0, 0, 0, 0)) {
        TimerSkeleton()
        Log.d("Timer.kt", "${smokingStats.value}")
    } else {
        Log.d("Timer.kt", "${smokingStats.value}")
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .shadow(5.dp, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(10.dp)),  // Then clip
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
                    .background(Color(0xFFCCA8E9))
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,  // Center items vertically
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = if (years > 0) years.toString() else days.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        color = Color(0xFF590D82)
                    )
                    Text(
                        text = if (years > 0) getCorrectWordForm(
                            years.toInt(),
                            "год",
                            "года",
                            "лет"
                        ) else getCorrectWordForm(days.toInt(), "день", "дня", "дней"),
                        fontSize = 18.sp,
                        color = Color(0xFF590D82)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFC3BEF0))
                    .height(90.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,  // Center items vertically
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = if (years > 0) months.toString() else hours.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        color = Color(0xFF590D82)
                    )
                    Text(
                        text = if (years > 0) getCorrectWordForm(
                            months.toInt(),
                            "месяц",
                            "месяца",
                            "месяцев"
                        ) else getCorrectWordForm(hours.toInt(), "час", "часа", "часов"),
                        fontSize = 18.sp,
                        color = Color(0xFF590D82)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(Color(0xFFCADEFC))
                    .height(90.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,  // Center items vertically
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        text = if (years > 0) hours.toString() else minutes.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 27.sp,
                        color = Color(0xFF590D82)
                    )
                    Text(
                        text = if (years > 0) getCorrectWordForm(
                            hours.toInt(),
                            "час",
                            "часа",
                            "часов"
                        ) else getCorrectWordForm(minutes.toInt(), "минута", "минуты", "минут"),
                        fontSize = 18.sp,
                        color = Color(0xFF590D82)
                    )
                }
            }
        }
    }
}

fun getCorrectWordForm(value: Int, form1: String, form2: String, form5: String): String {
    val absValue = value % 100
    if (absValue in 11..19) {
        return form5
    }
    return when (absValue % 10) {
        1 -> form1
        2, 3, 4 -> form2
        else -> form5
    }
}


@Composable
fun ShimmerEffect() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.9f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.9f)
    )

    val transition = rememberInfiniteTransition(label = "")
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = shimmerColors,
                    start = Offset.Zero,
                    end = Offset(x = translateAnim.value, y = translateAnim.value)
                )
            )
            .fillMaxSize()
    )
}

@Composable
fun TimerSkeleton() {
    Box {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp)
                .shadow(5.dp, RoundedCornerShape(6.dp))
                .clip(RoundedCornerShape(10.dp)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
            ) {
                ShimmerEffect()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
            ) {
                ShimmerEffect()
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(90.dp)
            ) {
                ShimmerEffect()
            }
        }
        Text(
            "Настраиваем вашу статистику...",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.ExtraBold,
            color = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            textAlign = TextAlign.Center
        )
    }

}