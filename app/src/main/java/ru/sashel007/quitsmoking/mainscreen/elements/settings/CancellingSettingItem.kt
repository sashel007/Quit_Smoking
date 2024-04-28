package ru.sashel007.quitsmoking.mainscreen.elements.settings

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.util.getTipsFromJson
import ru.sashel007.quitsmoking.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun CancellingSettingItem(title: String, navController: NavController) {
    Box {
        CancellingSettingItemImageButton(title, navController)
    }
}

@Composable
fun CancellingSettingItemImageButton(text: String, navController: NavController) {
    Box(
        modifier = Modifier
            .width(360.dp)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 6.dp)
            .clickable {
                navController.navigate("settings_cancellingsmoking")
            }, contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.cancelling_setting_item_btn),
            contentDescription = "Button Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(Modifier.width(88.dp))
            Text(
                text = text,
                color = Color.Black,
                fontSize = 17.sp,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Normal,
                lineHeight = 14.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CancellingTimeChoosing(
    userViewModel: UserViewModel, navController: NavController
) {
    val quitDate = remember { mutableStateOf(LocalDate.now()) }
    val quitTime = remember { mutableStateOf(LocalTime.now()) }
    val context = LocalContext.current
    val backButtonSize = 26.dp

    val dateSelected = remember { mutableStateOf(false) }
    val timeSelected = remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    fun resetSelection() {
        dateSelected.value = false
        timeSelected.value = false
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Top
    ) {

        /** Стрелка "НАЗАД", заголовок "Настройки даты */

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black),
                            startY = 0f,
                            endY = 100f
                        )
                    )
                    .align(Alignment.BottomCenter)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
            ) {
                Box(
                    Modifier
                        .padding(6.dp)
                        .align(Alignment.CenterStart)
                ) {
                    BackButtonImage(navController = navController, backButtonSize)
                }
                Text(
                    "Настройки даты",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        )

        /** Элемент с кнопками */
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(34.dp)
                        .wrapContentSize()
                        .padding(top = 12.dp)
                ) {
                    Text(
                        "Вы бросили курить:",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier
                            .clickable {
                                DatePickerDialog(
                                    context,
                                    { _, year, month, dayOfMonth ->
                                        quitDate.value = LocalDate.of(year, month + 1, dayOfMonth)
                                        dateSelected.value = true
                                    },
                                    quitDate.value.year,
                                    quitDate.value.monthValue - 1,
                                    quitDate.value.dayOfMonth
                                ).show()
                            }
                            .padding(8.dp)
                            .background(Color(0xFFFEFAE0))) {
                            Text(
                                text = quitDate.value.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
                                fontSize = 26.sp,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }
                        Text(" в ", fontSize = 26.sp, color = Color.Black)
                        Box(modifier = Modifier
                            .clickable {
                                TimePickerDialog(
                                    context, { _, hourOfDay, minute ->
                                        quitTime.value = LocalTime.of(hourOfDay, minute)
                                        timeSelected.value = true
                                    }, quitTime.value.hour, quitTime.value.minute, true
                                ).show()
                            }
                            .padding(8.dp)
                            .background(Color(0xFFFEFAE0))) {
                            Text(
                                quitTime.value.format(DateTimeFormatter.ofPattern("HH:mm")),
                                fontSize = 26.sp,
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                        }

                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.Center

                ) {
                    Text(
                        "Нажмите на дату и время,\nчтобы изменить",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        textAlign = TextAlign.Center,
                        lineHeight = 14.sp
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                saveQuitDetails(
                                    userViewModel = userViewModel,
                                    time = quitTime.value,
                                    date = quitDate.value,
                                    snackbarHostState = snackbarHostState
                                )
                            }
                        },
                        enabled = dateSelected.value || timeSelected.value,
                        modifier = Modifier
//                            .weight(1f)
                            .padding(start = 40.dp, end = 20.dp)
                            .wrapContentWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (dateSelected.value || timeSelected.value) {
                                Color(0xFFA9DEF9)
                            } else {
                                Color(0xFFE9ECEF)
                            }
                        )
                    ) {
                        Text(
                            "Применить",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.5f.sp
                        )
                    }

                    Button(
                        onClick = { resetSelection() },
                        modifier = Modifier
                            .padding(start = 6.dp, end = 26.dp)
                            .width(500.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            contentColor = if (dateSelected.value || timeSelected.value) {
                                Color(0xFFA9DEF9)
                            } else {
                                Color.LightGray
                            }
                        ),
                        enabled = dateSelected.value || timeSelected.value,
                    ) {
                        Text(
                            "Отменить",
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 2.sp,
                            maxLines = 1
                        )
                    }
                }
            }

        }

        Spacer(Modifier.height(10.dp))

        ExpandableBox(context)

    }

    Box(modifier = Modifier.wrapContentSize()) {
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter))
    }
}

@Composable
fun ExpandableBox(context: Context) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateDpAsState(
        targetValue = if (expanded) 180.dp else 180.dp,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )
    val tips = remember { getTipsFromJson(context) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .shadow(elevation = 4.dp, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 10.dp, top = 6.dp, bottom = 6.dp)
        ) {
            Text(
                text = "Как продержаться при желании закурить?",
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember {
                            MutableInteractionSource()
                        },
                        indication = null
                    ) {
                        expanded = !expanded
                    },
                lineHeight = 16.sp
            )
            IconButton(
                onClick = { expanded = !expanded }, modifier = Modifier.rotate(rotationAngle.value)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Раскрыть"
                )
            }
        }
        AnimatedVisibility(visible = expanded) {
            Text(
                text = tips,
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Light,
                lineHeight = 20.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
suspend fun saveQuitDetails(
    userViewModel: UserViewModel,
    date: LocalDate,
    time: LocalTime,
    snackbarHostState: SnackbarHostState
) {
    val quitDateTime = LocalDateTime.of(date, time)
    val quitTimeInMillis = quitDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    Log.d("QuitDateTime", "Quit time in millis: $quitTimeInMillis")
    userViewModel.updateQuitTimeInMillisec(quitTimeInMillis)
    snackbarHostState.showSnackbar(
        message = "Дата/время изменены",
        actionLabel = "Ок"
    )
}

