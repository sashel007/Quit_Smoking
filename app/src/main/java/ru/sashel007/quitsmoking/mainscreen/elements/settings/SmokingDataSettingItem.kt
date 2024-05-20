package ru.sashel007.quitsmoking.mainscreen.elements.settings

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.mainscreen.elements.InfoText
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.ui.theme.textFieldFocusedColor
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

@Composable
fun SmokingDataSettingItem(title: String, navController: NavController) {
    Box {
        SmokingDataSettingItemImageButton(title, navController)
    }
}

@Composable
fun SmokingDataSettingItemImageButton(
    text: String, navController: NavController
) {
    Box(
        modifier = Modifier
            .width(360.dp)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 6.dp)
            .clickable { navController.navigate("settings_userdata") },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.smoking_data_settings_item_btn),
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
                lineHeight = 20.sp
            )
        }
    }
}

@SuppressLint("UnrememberedMutableInteractionSource")
@Composable
fun UserSmokingDataChoosing(
    navController: NavController, userViewModel: UserViewModel
) {
    val userData by userViewModel.userData.observeAsState()
    Log.d("Composable_problem", "from Composable: $userData")
    val backButtonSize = 26.dp

    var cigarettesPerDay by remember {
        mutableStateOf(
            userData?.cigarettesPerDay?.toString() ?: ""
        )
    }
    var cigarettesInPack by rememberSaveable {
        mutableStateOf(
            userData?.cigarettesInPack?.toString() ?: ""
        )
    }
    var packCost by rememberSaveable { mutableStateOf(userData?.packCost?.toString() ?: "") }

    val initialCigarettesPerDay = userData?.cigarettesPerDay?.toString() ?: ""
    val initialCigarettesInPack = userData?.cigarettesInPack?.toString() ?: ""
    val initialPackCost = userData?.packCost?.toString() ?: ""

    var isChanged by rememberSaveable { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }
    Log.d("check variables", "isChanged = $isChanged \n showError = $showError")

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()

    val validateInput: (String) -> Boolean = { value ->
        value.isNotEmpty() && value.length <= 3 && value.toIntOrNull() != null && value.toInt() != 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical)
            .pointerInput(Unit) {
                detectTapGestures(onPress = { focusManager.clearFocus() })
            }, verticalArrangement = Arrangement.Top
    ) {
        /** Стрелка "НАЗАД", заголовок "Данные о курении" */
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
                    "Данные о курении",
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
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                val bulletPoint = "\u2022"

                Text(
                    "Введите данные о курении:",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
                Box(
                    modifier = Modifier
                        .border(2.dp, Color.Black, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 12.dp)
                        .height(200.dp)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color(0xFFFBF7EA), RoundedCornerShape(12.dp)),
                        ) {
                            Text(
                                text = "$bulletPoint Выкуренные сигареты за день",
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .weight(2f),
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Normal,
                            )
                            TextField(
                                value = cigarettesPerDay,
                                onValueChange = { value ->
                                    if (value.length <= 3 && (value.isEmpty() || !value.startsWith("0"))) {
                                        cigarettesPerDay = value
                                        isChanged =
                                            cigarettesPerDay != initialCigarettesPerDay || cigarettesInPack != initialCigarettesInPack || packCost != initialPackCost
                                        showError = !validateInput(cigarettesPerDay) || !validateInput(cigarettesInPack) || !validateInput(packCost)
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                ),
                                modifier = Modifier
                                    .focusRequester(focusRequester)
                                    .weight(1.3f)
                                    .padding(4.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = textFieldFocusedColor,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(
                                    fontFamily = MyTextStyles.mRobotoFontFamily,
                                    fontWeight = FontWeight.Light
                                )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color(0xFFFBF7EA), RoundedCornerShape(12.dp)),
                        ) {
                            Text(
                                text = "$bulletPoint Сигарет в пачке",
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .weight(2f),
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Normal,
                            )
                            TextField(
                                value = cigarettesInPack,
                                onValueChange = { value ->
                                    if (value.length <= 3 && (value.isEmpty() || !value.startsWith("0"))) {
                                        cigarettesInPack = value
                                        isChanged =
                                            cigarettesPerDay != initialCigarettesPerDay || cigarettesInPack != initialCigarettesInPack || packCost != initialPackCost
                                        showError = !validateInput(cigarettesPerDay) || !validateInput(cigarettesInPack) || !validateInput(packCost)
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                ),
                                modifier = Modifier
                                    .focusRequester(focusRequester)
                                    .weight(1.3f)
                                    .padding(4.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = textFieldFocusedColor,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(
                                    fontFamily = MyTextStyles.mRobotoFontFamily,
                                    fontWeight = FontWeight.Light
                                )
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .background(Color(0xFFFBF7EA), RoundedCornerShape(12.dp)),
                        ) {
                            Text(
                                text = "$bulletPoint Стоимость пачки",
                                fontFamily = MyTextStyles.mRobotoFontFamily,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .padding(start = 12.dp)
                                    .weight(2f)
                            )
                            TextField(
                                value = packCost,
                                onValueChange = { value ->
                                    if (value.length <= 3 && (value.isEmpty() || !value.startsWith("0"))) {
                                        packCost = value
                                        isChanged =
                                            cigarettesPerDay != initialCigarettesPerDay || cigarettesInPack != initialCigarettesInPack || packCost != initialPackCost
                                        showError = !validateInput(cigarettesPerDay) || !validateInput(cigarettesInPack) || !validateInput(packCost)
                                    }
                                },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        keyboardController?.hide()
                                        focusManager.clearFocus()
                                    }
                                ),
                                modifier = Modifier
                                    .focusRequester(focusRequester)
                                    .weight(1.3f)
                                    .padding(4.dp),
                                colors = TextFieldDefaults.colors(
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = textFieldFocusedColor,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                textStyle = TextStyle(
                                    fontFamily = MyTextStyles.mRobotoFontFamily,
                                    fontWeight = FontWeight.Light
                                )
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = {
                            val snackbarHostState = SnackbarHostState()

                            userViewModel.updateCigarettesPerDay(cigarettesPerDay.toInt())
                            userViewModel.updateCigarettesInPack(cigarettesInPack.toInt())
                            userViewModel.updatePackCost(packCost.toInt())
                            isChanged = false
                            focusRequester.freeFocus()
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Изменения применены", actionLabel = "Ок"
                                )
                            }
                            keyboardController?.hide()
                        },
                        enabled = isChanged && !showError,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFCE93D8),
                            disabledContainerColor = Color.LightGray
                        )
                    ) {
                        Text("Применить")
                    }

                    Button(
                        onClick = {
                            cigarettesPerDay = initialCigarettesPerDay
                            cigarettesInPack = initialCigarettesInPack
                            packCost = initialPackCost
                            isChanged = false
                            showError = false
                            focusManager.clearFocus()
                        },
                        enabled = isChanged,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFCE93D8),
                            disabledContainerColor = Color.LightGray
                        )
                    ) {
                        Text("Отменить")
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.padding(bottom = 26.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            InfoText()
        }
    }
}
