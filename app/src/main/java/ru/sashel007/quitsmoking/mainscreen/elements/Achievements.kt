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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.AchievementDto
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun Achievements(
    achievementsState: State<List<AchievementDto>?>,
    onAchievementClick: (AchievementDto) -> Unit,
    navController: NavController
) {
    val achievements by remember(achievementsState.value) {
        derivedStateOf {
            achievementsState.value
        }
    }
    val imageSize: Dp
    var startAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)
        startAnimation = true
    }

    var showDetail by remember { mutableStateOf<AchievementDto?>(null) }
    if (showDetail != null) {
        imageSize = 160.dp
        AchievementModalLayer(
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
                    modifier = Modifier.clickable {
                        navController.navigate("achievements_list")
                    })
            }
            LazyRow(
                contentPadding = PaddingValues(start = 6.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                achievements?.take(4)?.let { list ->
                    items(list) { achievement ->
                        AchievementBlock(
                            startAnimation,
                            achievement,
                            modifier = Modifier
                                .size(160.dp, 220.dp)
                                .clickable { onAchievementClick(achievement) },
                            onClick = { onAchievementClick(achievement) },
                            imageSize
                        )
                    }
                }
                item {
                    Box(
                        modifier = Modifier.height(172.dp), contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.open_all_achievs_btn),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(horizontal = 20.dp)
                                .background(Color.Transparent, RoundedCornerShape(100.dp))
                                .size(50.dp)
                                .clickable {
                                    navController.navigate("achievements_list")
                                },
                        )
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AchievementModalLayer(
    achievement: AchievementDto, startAnimation: Boolean, onDismiss: () -> Unit, imageSize: Dp
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
            Box(Modifier
                .size(276.dp, 344.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Transparent)
                .clickable(indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {}) {
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
    val imageMap = remember {
        mapOf(
            "achievement_1" to R.drawable.achievement_1,
            "achievement_2" to R.drawable.achievement_2,
            "achievement_3" to R.drawable.achievement_3,
            "achievement_4" to R.drawable.achievement_4,
            "achievement_5" to R.drawable.achievement_5,
            "achievement_6" to R.drawable.achievement_6,
            "achievement_7" to R.drawable.achievement_7,
            "achievement_8" to R.drawable.achievement_8,
            "achievement_9" to R.drawable.achievement_9,
            "achievement_10" to R.drawable.achievement_10,
            "achievement_11" to R.drawable.achievement_11,
            "achievement_12" to R.drawable.achievement_12,
            "achievement_13" to R.drawable.achievement_13,
            "achievement_14" to R.drawable.achievement_14,
            "achievement_15" to R.drawable.achievement_15,
            "achievement_16" to R.drawable.achievement_16,
            "achievement_17" to R.drawable.achievement_17,
            "achievement_18" to R.drawable.achievement_18,
            "achievement_19" to R.drawable.achievement_19,
            "achievement_20" to R.drawable.achievement_20,
            "achievement_21" to R.drawable.achievement_21,
            "achievement_22" to R.drawable.achievement_22,
            "achievement_23" to R.drawable.achievement_23,
            "achievement_24" to R.drawable.achievement_24,
            "achievement_25" to R.drawable.achievement_25,
            "achievement_26" to R.drawable.achievement_26,
            "achievement_27" to R.drawable.achievement_27,
            "achievement_28" to R.drawable.achievement_28,
            "achievement_29" to R.drawable.achievement_29,
            "achievement_30" to R.drawable.achievement_30,
            "achievement_31" to R.drawable.achievement_31,
            "achievement_32" to R.drawable.achievement_32,
            "achievement_33" to R.drawable.achievement_33,
            "achievement_34" to R.drawable.achievement_34,
            "achievement_35" to R.drawable.achievement_35
        )
    }

    val imageName = achievement.imageUri
    val imageResId = imageMap[imageName] ?: R.drawable.achievement_error

    val progress = achievement.progressLine
    val isUnlocked = achievement.isUnlocked
    val achievName = achievement.name
    val achievDescription = achievement.description

    val progressAnimationValue by animateFloatAsState(
        targetValue = if (startAnimation) progress.toFloat() else 0f,
        animationSpec = tween(900),
        label = ""
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
                    .shadow(
                        elevation = if (imageSize == 120.dp) 0.dp else 4.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(
                        if (imageSize == 120.dp) Color.Transparent else Color(0xFFFDE2FF)
                    )
                    .padding(10.dp), contentAlignment = Alignment.TopCenter
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

                    Text(
                        text = achievName,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = if (imageSize == 120.dp) 26.sp else 20.sp,
                        lineHeight = if (imageSize == 120.dp) 28.sp else 16.sp,
                        maxLines = if (imageSize == 120.dp) 10 else 4,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        textAlign = TextAlign.Center,
                        color = if (imageSize == 120.dp) Color.White else Color.Black,
                        modifier = Modifier
                            .width(if (imageSize == 120.dp) 300.dp else 160.dp)
                            .padding(
                                top = if (imageSize == 120.dp) 20.dp else 6.dp,
                                start = if (imageSize == 120.dp) 20.dp else 1.dp,
                                end = if (imageSize == 120.dp) 20.dp else 1.dp
                            )
                    )
                    Text(
                        text = achievDescription,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = if (imageSize == 120.dp) FontWeight.Normal else FontWeight.Light,
                        fontSize = if (imageSize == 120.dp) 26.sp else 14.sp,
                        maxLines = if (imageSize == 120.dp) 5 else 3,
                        lineHeight = if (imageSize == 120.dp) 24.sp else 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                        color = if (imageSize == 120.dp) Color.White else Color.Black,
                        modifier = Modifier
                            .width(if (imageSize == 120.dp) 300.dp else 160.dp)
                            .weight(1f)
                            .padding(
                                start = if (imageSize == 120.dp) 20.dp else 1.dp,
                                end = if (imageSize == 120.dp) 20.dp else 1.dp
                            ),
                        textAlign = if (imageSize == 120.dp) TextAlign.Center else TextAlign.Start
                    )
                }
            }

            if (!isUnlocked) {
                Box(
                    modifier = Modifier
                        .width(if (imageSize == 120.dp) 276.dp else 160.dp)
                        .height(if (imageSize == 120.dp) 344.dp else 220.dp)
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
                                            Color(0xFF2be4dc).copy(0.5f), Color(0xFF243484).copy(1f)
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



@Composable
fun AchievementsList(
    navController: NavController,
    achievements: List<AchievementDto>?,
) {
    val imageMap = remember {
        mapOf(
            "achievement_1" to R.drawable.achievement_1,
            "achievement_2" to R.drawable.achievement_2,
            "achievement_3" to R.drawable.achievement_3,
            "achievement_4" to R.drawable.achievement_4,
            "achievement_5" to R.drawable.achievement_5,
            "achievement_6" to R.drawable.achievement_6,
            "achievement_7" to R.drawable.achievement_7,
            "achievement_8" to R.drawable.achievement_8,
            "achievement_9" to R.drawable.achievement_9,
            "achievement_10" to R.drawable.achievement_10,
            "achievement_11" to R.drawable.achievement_11,
            "achievement_12" to R.drawable.achievement_12,
            "achievement_13" to R.drawable.achievement_13,
            "achievement_14" to R.drawable.achievement_14,
            "achievement_15" to R.drawable.achievement_15,
            "achievement_16" to R.drawable.achievement_16,
            "achievement_17" to R.drawable.achievement_17,
            "achievement_18" to R.drawable.achievement_18,
            "achievement_19" to R.drawable.achievement_19,
            "achievement_20" to R.drawable.achievement_20,
            "achievement_21" to R.drawable.achievement_21,
            "achievement_22" to R.drawable.achievement_22,
            "achievement_23" to R.drawable.achievement_23,
            "achievement_24" to R.drawable.achievement_24,
            "achievement_25" to R.drawable.achievement_25,
            "achievement_26" to R.drawable.achievement_26,
            "achievement_27" to R.drawable.achievement_27,
            "achievement_28" to R.drawable.achievement_28,
            "achievement_29" to R.drawable.achievement_29,
            "achievement_30" to R.drawable.achievement_30,
            "achievement_31" to R.drawable.achievement_31,
            "achievement_32" to R.drawable.achievement_32,
            "achievement_33" to R.drawable.achievement_33,
            "achievement_34" to R.drawable.achievement_34,
            "achievement_35" to R.drawable.achievement_35
        )
    }
    val startAnimation by remember { mutableStateOf(true) }

    val lazyListState = rememberLazyListState()
    val scrollEffects = remember(lazyListState) {
        derivedStateOf {
            val isScrolled =
                lazyListState.firstVisibleItemScrollOffset > 150 || lazyListState.firstVisibleItemIndex > 0
            Triple(
                if (isScrolled) Color.White else Color.Transparent, // Background color
                if (isScrolled) RoundedCornerShape(
                    0.dp, 0.dp, 12.dp, 12.dp
                ) else RectangleShape, // Shape
                if (isScrolled) 8.dp else 0.dp // Shadow elevation
            )
        }
    }
    val (backgroundColor, shape, shadowElevation) = scrollEffects.value

    var modalWindow by remember { mutableStateOf<AchievementDto?>(null) }

    Box {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = shadowElevation, shape = shape)
                    .clip(shape)
                    .background(backgroundColor)
            ) {
                Box(modifier = Modifier.align(Alignment.TopStart)) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
                Box(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        "Достижения",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }
            }

            LazyColumn(state = lazyListState, contentPadding = PaddingValues(12.dp)) {
                achievements?.let {
                    itemsIndexed(it) { index, achievement ->
                        val progress = achievement.progressLine
                        val progressAnimationValue by animateFloatAsState(
                            targetValue = if (startAnimation) progress.toFloat() else 0f,
                            animationSpec = tween(900),
                            label = ""
                        )
                        val imageId = imageMap[achievement.imageUri] ?: R.drawable.achievement_error
                        val isUnlocked = achievement.isUnlocked
                        val name = achievement.name
                        val description = achievement.description

                        if (!isUnlocked) {
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .clickable { modalWindow = achievement }) {
                                Box(contentAlignment = Alignment.Center) {
                                    Image(
                                        painter = painterResource(id = imageId),
                                        contentDescription = "",
                                        modifier = Modifier.size(60.dp),
                                    )
                                    Box(
                                        modifier = Modifier
                                            .size(60.dp)
                                            .background(Color.Gray, RoundedCornerShape(100))
                                    )
                                    CircularProgressIndicator(
                                        progress = progressAnimationValue / 100,
                                        modifier = Modifier
                                            .size(80.dp)
                                            .align(Alignment.Center),
                                        color = Color.Yellow,
                                        trackColor = Color.Gray.copy(alpha = 0.8f),
                                        strokeWidth = 12.dp,
                                        strokeCap = StrokeCap.Round
                                    )
                                    Box {
                                        Text(
                                            text = "$progress%",
                                            fontFamily = MyTextStyles.mRobotoFontFamily,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 24.sp,
                                            color = Color.Black
                                        )
                                    }
                                }

                                Spacer(Modifier.width(20.dp))

                                Column(modifier = Modifier.fillMaxHeight()) {
                                    Text(
                                        text = name,
                                        fontSize = 18.sp,
                                        fontFamily = MyTextStyles.mRobotoFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Start,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = true,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = description,
                                        fontFamily = MyTextStyles.mRobotoFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Gray,
                                        lineHeight = 18.sp,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = true
                                    )
                                }
                            }
                        } else {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .clickable { modalWindow = achievement }
                                .padding(vertical = 8.dp, horizontal = 12.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = imageId),
                                    contentDescription = "",
                                    modifier = Modifier.size(60.dp),
                                )
                                Spacer(Modifier.width(28.dp))
                                Column(modifier = Modifier.fillMaxHeight()) {
                                    Text(
                                        text = name,
                                        fontSize = 18.sp,
                                        fontFamily = MyTextStyles.mRobotoFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Start,
                                        maxLines = 2,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = true,
                                        modifier = Modifier.padding(bottom = 4.dp)
                                    )
                                    Text(
                                        text = description,
                                        fontFamily = MyTextStyles.mRobotoFontFamily,
                                        fontWeight = FontWeight.Normal,
                                        color = Color.Gray,
                                        lineHeight = 18.sp,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                        softWrap = true
                                    )
                                }
                            }
                        }
                        if (index < it.size - 1) {
                            HorizontalDivider(
                                color = Color.LightGray,
                                thickness = 1.dp,
                                modifier = Modifier.padding(vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }
        if (modalWindow != null) {
            AchievementModalLayer(
                achievement = modalWindow!!,
                startAnimation = true,
                onDismiss = { modalWindow = null },
                imageSize = 120.dp
            )
        }
    }
}