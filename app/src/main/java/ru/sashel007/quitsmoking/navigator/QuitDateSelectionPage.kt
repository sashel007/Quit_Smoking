package ru.sashel007.quitsmoking.navigator

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
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
import androidx.compose.runtime.mutableIntStateOf
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
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.viewmodel.UserViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 *  Страница "Когда вы бросили курить?"
 */

@Composable
fun QuitDateSelectionPage(
    userViewModel: UserViewModel,
    navController: NavController,
    onClickForward: () -> Unit
    ) {
    val quitDate =
        remember { mutableStateOf(Calendar.getInstance().time) }
    val quitTime = remember { mutableIntStateOf(0) }
    val context = LocalContext.current

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
                BackButtonImage(navController = navController)
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
                style = MyTextStyles.startingLittleTextStyle
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                // Отображение текущей даты и времени
                val currentDate = LocalDate.now()
                val currentTime = LocalTime.now()
                val russianLocale = Locale("ru")
                val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy", russianLocale)
                val timeFormatter = DateTimeFormatter.ofPattern("HH:mm", russianLocale)
                Text(
                    text = "${currentDate.format(dateFormatter)} в ${
                        currentTime.format(
                            timeFormatter
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
                        val calendar = Calendar.getInstance()
                        DatePickerDialog(
                            context,
                            { _, year, month, dayOfMonth ->
                                calendar.set(year, month, dayOfMonth)
                                quitDate.value = calendar.time
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
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
                        text = stringResource(id = R.string.choose_date),
                        style = MyTextStyles.buttonTextStyle
                    )
                }

                /** "ВЫБРАТЬ ВРЕМЯ" */

                Button(
                    onClick = {
                        val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
                        val currentMinute = Calendar.getInstance().get(Calendar.MINUTE)

                        TimePickerDialog(
                            context,
                            { _, hourOfDay, minute ->
                                quitTime.intValue = hourOfDay * 60 + minute
                            },
                            currentHour,
                            currentMinute,
                            true  // Use 24-hour format or false for 12-hour format
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
                    onClick = { TODO() },
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
                    saveQuitDetails(userViewModel, quitDate.value, quitTime.intValue)
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

fun saveQuitDetails(userViewModel: UserViewModel, date: Date, timeInMinutes: Int) {
    // Конвертация Date в милисекунлы
    val quitDateInMillis = date.time
    Log.d("Test_2", "$quitDateInMillis")

    // Вызов ViewModel для обновления значений о времени
    userViewModel.updateQuitTimeInMillisec(quitDateInMillis)
//    userViewModel.updateQuitTime(timeInMinutes)
}
