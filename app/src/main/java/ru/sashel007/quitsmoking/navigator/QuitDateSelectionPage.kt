package ru.sashel007.quitsmoking.navigator

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.viewmodel.UserViewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar

/**
 *  Страница "Когда вы бросили курить?"
 */


@Composable
fun QuitDateSelectionPage(
    userViewModel: UserViewModel,
    navController: NavController,
    onClickForward: () -> Unit
) {
    val quitDate = remember { mutableStateOf(LocalDate.now()) }
    val quitTime = remember { mutableStateOf(LocalTime.now()) }
    val context = LocalContext.current
    val buttonsSize = 32.dp

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveQuitDetails(date: LocalDate, time: LocalTime) {
        val quitDateTime = LocalDateTime.of(date, time)
        val quitTimeInMillis =
            quitDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        Log.d("QuitDateTime", "Quit time in millis: $quitTimeInMillis")
        userViewModel.updateQuitTimeInMillisec(quitTimeInMillis)
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
        ) {

            /** Стрелка "НАЗАД" */

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                BackButtonImage(navController = navController, buttonsSize)
            }

            Spacer(modifier = Modifier.height(48.dp))

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp),
                    painter = painterResource(id = R.drawable.calendar_second),
                    contentDescription = "Изображение календаря",
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = stringResource(id = R.string.quite_smoking),
                    fontWeight = FontWeight.Bold,
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontSize = 33.sp,
                    lineHeight = 28.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.violette)
                )
            }

            Text(
                text = stringResource(id = R.string.if_you_keep_smoking),
                maxLines = 2,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                style = MyTextStyles.startingLittleTextStyle
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${quitDate.value.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))} в ${
                        quitTime.value.format(
                            DateTimeFormatter.ofPattern("HH:mm")
                        )
                    }",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.violette),
                    textAlign = TextAlign.Center,
                )
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                /** "ВЫБРАТЬ ДАТУ" */

                Button(
                    onClick = {
                        val datePickerDialog = DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                quitDate.value = LocalDate.of(year, month + 1, dayOfMonth)
                            },
                            quitDate.value.year,
                            quitDate.value.monthValue - 1,
                            quitDate.value.dayOfMonth
                        )
                        datePickerDialog.datePicker.apply {
                            val minDateCalendar = Calendar.getInstance().apply {
                                set(
                                    1940,
                                    Calendar.JANUARY,
                                    1
                                )  // Устанавливаем минимальную дату 1 января 1940 года
                            }
                            val maxDateCalendar =
                                Calendar.getInstance()  // Максимальная дата сегодня
                            minDate = minDateCalendar.timeInMillis
                            maxDate = maxDateCalendar.timeInMillis
                        }
                        datePickerDialog.show()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        text = stringResource(id = R.string.choose_date),
                        style = MyTextStyles.buttonTextStyle
                    )
                }

                /** "ВЫБРАТЬ ВРЕМЯ" */

                Button(
                    onClick = {
                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                val selectedTime = LocalTime.of(hourOfDay, minute)
                                val currentDateTime = LocalDateTime.now()
                                val selectedDateTime = LocalDateTime.of(quitDate.value, selectedTime)

                                if (selectedDateTime.isBefore(currentDateTime) || selectedDateTime.isEqual(currentDateTime)) {
                                    quitTime.value = selectedTime
                                } else {
                                    // Вывести сообщение пользователю о недопустимом времени
                                }
                            },
                            quitTime.value.hour,
                            quitTime.value.minute,
                            true
                        ).show()
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                ) {
                    Text(
                        stringResource(id = R.string.pick_time),
                        style = MyTextStyles.buttonTextStyle
                    )
                }
            }

            /** "СЕЙЧАС" */

            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Button(
                    onClick = {
                        quitDate.value = LocalDate.now()
                        quitTime.value = LocalTime.now()
                        saveQuitDetails(quitDate.value, quitTime.value)
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .border(3.dp, colorResource(R.color.violette), RoundedCornerShape(8.dp))
                        .width(120.dp),
                    contentPadding = PaddingValues(1.dp)

                ) {
                    Text(stringResource(id = R.string.now))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Spacer(modifier = Modifier.height(100.dp))

            /** КНОПКА ДАЛЬШЕ */

            Button(
                onClick = {
                    saveQuitDetails(quitDate.value, quitTime.value)
                    onClickForward()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 2.dp, top = 5.dp, end = 2.dp, bottom = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = MyTextStyles.bigButtonTextStyle
                )
            }
        }
    }


}


