package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import java.time.format.TextStyle

@Composable
fun BreathScreen(navController: NavController) {
    val colorStops = arrayOf(
        0.0f to Color(0xFFFAAFDC), 0.5f to Color(0xFF948DB0), 1.0f to Color(0xFF326D86)
    )

    Box(modifier = Modifier
        .height(160.dp)
        .fillMaxWidth()
        .padding(14.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { navController.navigate("breath_greetings") }
        .background(Brush.linearGradient(colorStops = colorStops))

    ) {
        Text(
            "Дыхательные практики",
            color = Color.White,
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.lungs_icon),
            contentDescription = "Как ваше настроение",
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

@Composable
fun BreathGreetingScreen(navController: NavController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }
        Column(
            verticalArrangement = Arrangement.Center, modifier = Modifier.align(Alignment.Center)
        ) {
            Spacer(modifier = Modifier.size(160.dp))
            Image(
                painter = painterResource(id = R.drawable.breath_greeting_img),
                contentDescription = null,
                modifier = Modifier
                    .size(260.dp)
                    .padding(horizontal = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(R.string.square_breath),
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.size(10.dp))
            Text(
                text = stringResource(R.string.try_breathable_practices),
                color = Color.Gray,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Light,
                lineHeight = 20.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.size(40.dp))
            OutlinedButton(
                onClick = {
                    navController.navigate("breath_practice") {
                        popUpTo("breath_greatings") {
                            inclusive = true
                        }
                    }
                },
                modifier = Modifier
                    .padding(bottom = 60.dp)
                    .align(Alignment.CenterHorizontally),
            ) {
                Text(
                    text = "Попробовать",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Light,
                )
            }
        }

    }
}

@Composable
fun BreathPractice(navController: NavController) {
    val coroutineScope = rememberCoroutineScope()
    val squareSize = 700f // Размер стороны квадрата
    val dotRadius = 50f  // Радиус кружка
    val animatable = remember {
        Animatable(Offset(0f, 0f), Offset.VectorConverter)
    }
    var startAnimation by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableIntStateOf(0) }
    var timeSelected by remember { mutableIntStateOf(64) }

    val breathingPhases = listOf("Плавно вдохните", "Задержите дыхание", "Выдохните", "Не дышите")
    var currentPhase by remember { mutableIntStateOf(0) }
    var breathIndicator by remember { mutableStateOf("") }

    fun startBreathingCycle() {
        startAnimation = true
        remainingTime = timeSelected
        currentPhase = 0
    }

// Анимация дыхания
    if (startAnimation) {
        LaunchedEffect(key1 = "animation") {
            repeat(4) {
                for (i in 0 until 4) {
                    currentPhase = i
                    breathIndicator = breathingPhases[currentPhase % 4]  // Прямое присвоение значения
                    animatable.animateTo(
                        targetValue = when (i) {
                            0 -> Offset(squareSize, 0f)
                            1 -> Offset(squareSize, squareSize)
                            2 -> Offset(0f, squareSize)
                            else -> Offset(0f, 0f)
                        }, animationSpec = tween(durationMillis = 4000)
                    )
                }
            }
            startAnimation = false
        }
    }

// Управление таймером
    if (startAnimation) {
        LaunchedEffect(key1 = "timer") {
            while (remainingTime > 0) {
                delay(1000L)
                remainingTime--
            }
        }
    }


    val timeOptions = listOf(64, 128, 192, 256) // Время в секундах
    var showTimePickerDialog by remember { mutableStateOf(false) }
    if (showTimePickerDialog) {
        AlertDialog(onDismissRequest = { showTimePickerDialog = false },
            title = { Text("Выберите длительность:") },
            text = {
                Column {
                    timeOptions.forEach { time ->
                        TextButton(onClick = {
                            timeSelected = time
                            showTimePickerDialog = false
                        }) {
                            Text(
                                "${time / 60} минута ${time % 60} секунд",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {
                OutlinedButton(onClick = { showTimePickerDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }

    BackHandler {
        navController.navigate("mainScreen")
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
        ) {
            IconButton(onClick = { navController.navigate("mainScreen") }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
            }
        }
        Text(
            text = breathIndicator,
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .alpha(if (startAnimation) 1f else 0f)
                .align(Alignment.Center)
                .padding(bottom = 220.dp)
        )
        Column(
            modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Спокойное дыхание",
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                modifier = Modifier.padding(top = 70.dp, bottom = 10.dp)
            )
            Text(
                "Освободите голову от мыслей",
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 40.dp)
            )


            Canvas(
                modifier = Modifier
                    .size(240.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                val square = Rect(Offset.Zero, Offset(squareSize, squareSize))
                drawRect(
                    color = Color.Black,
                    topLeft = square.topLeft,
                    size = square.size,
                    style = Stroke(width = 3f)
                )
                drawCircle(
                    color = Color.Red, center = animatable.value, radius = dotRadius
                )
            }
            Spacer(Modifier.size(40.dp))
            Text(
                text = "Оставшееся время: ${
                    String.format("%02d:%02d", remainingTime / 60, remainingTime % 60)
                }",
                fontSize = 18.sp,
                modifier = Modifier.alpha(if (startAnimation) 1f else 0f)
                    .padding(bottom = 26.dp)
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(Modifier.size(20.dp))
                OutlinedButton(onClick = {
                    if (!startAnimation) {
                        remainingTime = timeSelected

                        startAnimation = true
                    } else {
                        coroutineScope.launch {
                            animatable.snapTo(Offset(0f, 0f))
                            remainingTime = 0
                        }
                        startAnimation = false
                    }
                }) {
                    Icon(
                        painter = if (!startAnimation) painterResource(id = R.drawable.baseline_play_arrow_24) else painterResource(
                            id = R.drawable.baseline_stop_24
                        ),
                        contentDescription = if (!startAnimation) "Play Button" else "Stop Button",
                        tint = Color.Black, // Цвет иконки
                        modifier = Modifier
                            .padding(8.dp)
                            .size(26.dp) // Размер иконки
                    )
                }
                Spacer(Modifier.size(40.dp))
                Box(modifier = Modifier.height(60.dp)) {
                    OutlinedButton(
                        onClick = { showTimePickerDialog = true }, enabled = !startAnimation
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_access_alarm_24),
                            contentDescription = "Set time for breathing",
                            tint = Color.Black, // Цвет иконки
                            modifier = Modifier.size(24.dp), // Размер иконки ,
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.Black, RoundedCornerShape(40.dp))
                            .padding(horizontal = 6.dp)
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            "${timeSelected / 60} мин.",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            fontSize = 12.sp
                        )
                    }
                }

            }

        }
    }
}