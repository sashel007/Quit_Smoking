package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun Achievements(
    achievementsState: State<List<AchievementDto>?>,
    onAchievementClick: (AchievementDto) -> Unit
) {
    val achievements by remember(achievementsState.value) {
        derivedStateOf {
            achievementsState.value
        }
    }
    val imageSize: Dp
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500) // Или другой подходящий способ определения видимости
        startAnimation = true
    }

    var showDetail by remember { mutableStateOf<AchievementDto?>(null) }
    if (showDetail != null) {
        imageSize = 160.dp
        ModalLayer(
            achievement = showDetail!!,
            startAnimation = startAnimation,
            onDismiss = { showDetail = null },
            imageSize
        )
    } else {
        imageSize = 80.dp
    }

    Box(
        modifier = Modifier
            .width(360.dp)
            .height(244.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 12.dp, topEnd = 12.dp, bottomStart = 14.dp, bottomEnd = 14.dp
                )
            )
            .padding(top = 1.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 2.dp),
            verticalArrangement = Arrangement.Top
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp, top = 1.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.achievement_title),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = stringResource(R.string.show_all),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black,
                    modifier = Modifier.clickable {})
            }
            LazyRow(
                contentPadding = PaddingValues(start = 6.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                achievements?.let {
                    items(it) { achievement ->
                        AchievementBlock(
                            startAnimation,
                            achievement,
                            modifier = Modifier
                                .size(160.dp, 172.dp)
                                .clickable { onAchievementClick(achievement) },
                            onClick = { onAchievementClick(achievement) },
                            imageSize
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ModalLayer(
    achievement: AchievementDto,
    startAnimation: Boolean,
    onDismiss: () -> Unit,
    imageSize: Dp
) {
    val isVisible = remember { mutableStateOf(true) }

    AnimatedVisibility(
        visible = isVisible.value,
        enter = scaleIn(animationSpec = tween(durationMillis = 300)),
        exit = scaleOut(animationSpec = tween(durationMillis = 300)),
        initiallyVisible = false
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable {
                    isVisible.value = false
                    onDismiss()
                }, contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .size(276.dp, 344.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .clickable(indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {}
            ) {
                AchievementBlock(
                    startAnimation = startAnimation,
                    achievement = achievement,
                    modifier = Modifier.fillMaxSize(),
                    onClick = onDismiss,
                    imageSize = imageSize
                )
            }
        }
    }
}

@Composable
fun AchievementBlock(
    startAnimation: Boolean,
    achievement: AchievementDto,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    imageSize: Dp
) {
    val context = LocalContext.current
    val imageName = achievement.imageUri
    val imageResId = context.resources.getIdentifier(
        imageName, "drawable", context.packageName
    )
    val progress = achievement.progressLine
    val isUnlocked = achievement.isUnlocked
    val description = achievement.name

    val progressAnimationValue by animateFloatAsState(
        targetValue = if (startAnimation) progress.toFloat() else 0f,
        animationSpec = tween(900), label = ""
    )


    Box {
        Box(
            modifier
                .background(Color.Transparent)
                .padding(start = if (imageSize == 120.dp) 0.dp else 6.dp)
                .clickable(onClick = onClick)
        ) {
            Box(
                modifier
                    .clip(shape = RoundedCornerShape(8.dp))
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp))
                    .background(
                        Color(
                            red = 0.7372549f, green = 0.7372549f, blue = 0.9137255f, alpha = 1f
                        )
                    )
                    .padding(10.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Box {
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = "Достижение_1",
                            modifier = Modifier
                                .size(if (imageSize == 120.dp) 140.dp else 80.dp)
                                .padding(top = if (imageSize == 120.dp) 20.dp else 1.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = description,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = if (imageSize == 120.dp) 26.sp else 16.sp,
                        lineHeight = if (imageSize == 120.dp) 28.sp else 16.sp,
                        maxLines = if (imageSize == 120.dp) 10 else 4,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        color = Color.Black,
                        modifier = Modifier
                            .width(if (imageSize == 120.dp) 300.dp else 160.dp)
                            .weight(1f)
                            .padding(
                                top = if (imageSize == 120.dp) 20.dp else 6.dp,
                                start = if (imageSize == 120.dp) 20.dp else 1.dp,
                                end = if (imageSize == 120.dp) 20.dp else 1.dp
                            )

                    )
                }

            }
            if (!isUnlocked) {
                Box(
                    modifier = Modifier
                        .width(if (imageSize == 120.dp) 276.dp else 160.dp)
                        .height(if (imageSize == 120.dp) 344.dp else 172.dp)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .background(Color.LightGray.copy(alpha = 0.6f))
                        .padding(10.dp),
                )
            }

            if (!isUnlocked) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(if (imageSize == 120.dp) 180.dp else 88.dp)
                        .align(Alignment.Center)
                ) {
                    if (imageSize == 120.dp) {
                        Box(
                            modifier = Modifier
                                .shadow(40.dp, RoundedCornerShape(100))
                                .clip(RoundedCornerShape(100))
                                .background(
                                    Brush.radialGradient(
                                        listOf(
                                            Color(0xFF2be4dc).copy(0.5f),
                                            Color(0xFF243484).copy(1f)
                                        )
                                    )
                                )
                                .size(180.dp)
                        )
                        Text(
                            text = "$progress%",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 70.sp,
                            color = Color.Yellow,
                            modifier = Modifier
                        )
                    } else {
                        CircularProgressIndicator(
                            progress = { progressAnimationValue / 100 },
                            modifier = Modifier
                                .shadow(
                                    elevation = if (imageSize == 120.dp) 1.dp else 4.dp,
                                    RoundedCornerShape(if (imageSize == 120.dp) 1.dp else 100.dp)
                                )
                                .size(if (imageSize == 120.dp) 120.dp else 80.dp)
                                .align(Alignment.Center),
                            color = Color.Yellow,
                            trackColor = Color.Gray.copy(alpha = 0.8f),
                            strokeWidth = if (imageSize == 120.dp) 16.dp else 12.dp,
                            strokeCap = StrokeCap.Round
                        )
                        Text(
                            text = "$progress%",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = if (imageSize == 120.dp) 60.sp else 24.sp,
                            color = if (imageSize == 120.dp) Color.White else Color.Black
                        )

                    }

                }
            }
        }
    }
}