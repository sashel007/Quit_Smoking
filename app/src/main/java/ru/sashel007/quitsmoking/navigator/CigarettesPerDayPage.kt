package ru.sashel007.quitsmoking.navigator

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.mainscreen.elements.MyCustomTextField
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

/** PAGE: Сколько сигарет вы выкуриваете за день? */

@Composable
fun CigarettesPerDayPage(
    navController: NavController,
    onClickForward: () -> Unit
) {
    var cigarettesCount by remember { mutableStateOf("") }
    val userViewModel: UserViewModel = viewModel()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            /**  Стрелка "НАЗАД" */

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                BackButtonImage(navController = navController)
            }

            Spacer(modifier = Modifier.height(34.dp))

            /** КАРТИНКА */

            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    modifier = Modifier
                        .size(120.dp),
                    painter = painterResource(id = R.drawable.puppy),
                    contentDescription = "Your Image Description",
                )
            }

            /** "Cколько сигарет вы выкуривали" */

            Text(
                textAlign = TextAlign.Center,
                text = stringResource(id = R.string.how_much_cigarettes),
                style = MyTextStyles.startingLittleTextStyle
            )

            Spacer(modifier = Modifier.height(16.dp))

            /** ТЕКСТОВОЕ ПОЛЕ --> ВВЕДИТЕ КОЛ-ВО СИГАРЕТ */

            MyCustomTextField(
                value = cigarettesCount,
                onValueChange = { newValue ->
                    cigarettesCount = newValue
                },
                label = { Text(stringResource(id = R.string.cigarettes_amount)) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            /** КНОПКА ДАЛЕЕ */

            Button(
                onClick = {
                    try {
                        val updatedCigaretteCount = cigarettesCount.toInt()
                        userViewModel.updateCigarettesPerDay(updatedCigaretteCount)
                        onClickForward()
                    } catch (e: NumberFormatException) {
                        Log.e("CigarettesPage", "Invalid number format", e)
                        // Показать ошибку пользователю
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 2.dp, top = 5.dp, end = 2.dp, bottom = 2.dp),
                shape = RoundedCornerShape(8.dp),
                enabled = cigarettesCount != "", // Кнопка активна когда есть текст
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (cigarettesCount != "" && cigarettesCount.isNotEmpty()) Color.Blue else Color.Gray
                )
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    style = MyTextStyles.bigButtonTextStyle
                )
            }

            Spacer(
                modifier = Modifier
                    .height(32.dp)
                    .weight(1f)
            )

            /** КЛАВИАТУРА */

            Column(
                modifier = Modifier.padding(bottom = 24.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                // Разбиваем числа на ряды
                val rows = listOf(
                    listOf(1, 2, 3),
                    listOf(4, 5, 6),
                    listOf(7, 8, 9),
                    listOf(null, 0, null) // Для четвертого ряда: пустое место, 0, пустое место
                )
                val interactionSource =
                    remember { MutableInteractionSource() } // Для кастомной ряби

                rows.forEachIndexed { rowIndex, row ->
                    if (rowIndex > 0) {
                        // Вертикальный Spacer между рядами для увеличения расстояния
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEachIndexed { columnIndex, number ->
                            when {
                                rowIndex == 3 && columnIndex == 2 -> {
                                    // Место для кнопки удаления в четвертом ряду справа
                                    Button(
                                        onClick = {
                                            if (cigarettesCount.isNotEmpty()) {
                                                cigarettesCount = cigarettesCount.dropLast(1)
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .indication(
                                                interactionSource,
                                                rememberRipple(
                                                    bounded = true,
                                                    color = Color.LightGray
                                                )
                                            ),
                                        shape = RoundedCornerShape(8.dp),
                                        interactionSource = interactionSource,
                                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.back_button), // Убедитесь, что ресурс существует
                                            contentDescription = "Удалить",
                                            tint = Color.Black
                                        )
                                    }
                                }

                                rowIndex == 3 && columnIndex == 0 -> {
                                    // Пустое место слева от 0 в четвертом ряду
                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                    )
                                }

                                rowIndex == 3 && columnIndex == 1 -> {
                                    // Кнопка с числом 0
                                    Button(
                                        onClick = {
                                            if (cigarettesCount == "0") {
                                                cigarettesCount = number.toString()
                                            } else {
                                                cigarettesCount += number.toString()
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp)
                                            .indication(
                                                interactionSource,
                                                rememberRipple(
                                                    bounded = true,
                                                    color = Color.LightGray
                                                )
                                            ),
                                        shape = RoundedCornerShape(8.dp),
                                        interactionSource = interactionSource,
                                        colors = ButtonDefaults.buttonColors(Color.Transparent)
                                    ) {
                                        Text(
                                            text = number.toString(),
                                            style = MyTextStyles.numberButtonTextStyle
                                        )
                                    }
                                }

                                else -> {
                                    // Кнопки с числами от 1 до 9
                                    Button(
                                        onClick = {
                                            if (cigarettesCount == "0") {
                                                cigarettesCount = number.toString()
                                            } else {
                                                cigarettesCount += number.toString()
                                            }
                                        },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(48.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White, // Цвет фона кнопки в нормальном состоянии
                                            contentColor = Color.White // Цвет контента (текст, иконка) кнопки
                                        )
                                    ) {
                                        Text(
                                            text = number.toString(),
                                            style = MyTextStyles.numberButtonTextStyle
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}