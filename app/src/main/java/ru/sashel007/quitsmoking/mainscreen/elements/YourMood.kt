package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.delay
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun YourMood() {
    val showDialog = remember { mutableStateOf(false) }
    val colorStops = arrayOf(
        0.0f to Color(0xFFC5CAE9), 0.2f to Color(0xFFB0BCE2), 1f to Color(0xFFE6E9FF)
    )
    var selectedImageId by remember { mutableStateOf<Int?>(null) }

    Box(modifier = Modifier
        .size(160.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { showDialog.value = true }
        .background(Brush.linearGradient(colorStops = colorStops))
        .padding(8.dp)) {
        Text(
            "Как ваше настроение?",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 4.dp, top = 4.dp)
        )
        Image(painter = painterResource(id = R.drawable.emoji_icon),
            contentDescription = "Как ваше настроение",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .graphicsLayer {
                    translationY = 16.dp.toPx()
                    translationX = 16.dp.toPx()
                }
                .clip(CircleShape)
                .shadow(10.dp),
            contentScale = ContentScale.Crop)
    }

    if (showDialog.value) {
        MoodDialog(onDismiss = { showDialog.value = false }) { imageId ->
            selectedImageId = imageId  // Вызов MoodSelector с выбранным изображением
        }
    }

    selectedImageId?.let {
        MoodSelector(imageId = it) {
            selectedImageId = null
        }
    }
}

@Composable
fun MoodDialog(onDismiss: () -> Unit, onSelectImage: (Int) -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(300.dp, 260.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Какое у вас сегодня настроение?",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Text(
                    "Выберите подходящий смайлик:",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    listOf(
                        R.drawable.happy_emogi_mood,
                        R.drawable.slight_smile_emoji_mood,
                        R.drawable.sunglasses_emoji_mood,
                        R.drawable.sleeping_emoji_mood,
                        R.drawable.rage_emoji_mood,
                        R.drawable.spiral_eyes_emoji_mood
                    ).forEach { imageId ->
                        Image(
                            painter = painterResource(id = imageId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .padding(2.dp)
                                .clickable {
                                    onSelectImage(imageId)
                                    onDismiss() // Закрывает диалог
                                }
                        )
                    }
                }
                OutlinedButton(
                    onClick = onDismiss, modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Закрыть")
                }
            }
        }
    }
}

@Composable
fun MoodSelector(imageId: Int, onDismiss: () -> Unit) {
    val isImageExpanded by remember { mutableStateOf(true) }
    var showText by remember { mutableStateOf(false) }
    val scale: Float by animateFloatAsState(
        targetValue = if (isImageExpanded) 5f else 1f,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    LaunchedEffect(isImageExpanded) {
        delay(500)
        showText = true
        delay(1500)
        onDismiss()
    }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = painterResource(id = imageId),
                    contentDescription = "Mood Image",
                    modifier = Modifier
                        .size(60.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        }
                )
                Spacer(modifier = Modifier.height(150.dp))

                AnimatedVisibility(
                    visible = showText,
                    enter = slideInHorizontally() + fadeIn(),
                    exit = fadeOut()
                ) {
                    Text(
                        "Ваше настроение учтено!",
                        color = Color.Yellow,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        lineHeight = 38.sp,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}