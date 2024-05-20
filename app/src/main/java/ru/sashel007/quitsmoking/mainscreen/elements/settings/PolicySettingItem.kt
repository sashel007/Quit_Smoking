package ru.sashel007.quitsmoking.mainscreen.elements.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.mappers.Disclaimer
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.mainscreen.elements.InfoText
import ru.sashel007.quitsmoking.navigator.NavRoutes
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.util.loadDisclaimer


@Composable
fun PolicySettingItem(title: String, navController: NavController) {
    Box {
        PolicySettingItemImageButton(title, navController)
    }
}

@Composable
fun PolicySettingItemImageButton(text: String, navController: NavController) {
    Box(
        modifier = Modifier
            .width(360.dp)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 6.dp)
            .clickable { navController.navigate(NavRoutes.SETTINGS_POLICY_PAGE) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.policy_settings_item_btn),
            contentDescription = "Button Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
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

@Composable
fun PolicyPage(navController: NavController) {
    val backButtonSize = 24.dp
    val uriHandler = LocalUriHandler.current
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val disclaimer = loadDisclaimer(context)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Top,
    ) {
        /** Стрелка "НАЗАД", заголовок */

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
                    "Политика\nконфиденциальности",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }
        }

        Spacer(
            modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 18.dp)
            ) {
                Text(
                    "© 2024 Sidorkin Alexander",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal
                )
                ClickableEmailText("i@sashel007.ru", uriHandler)
                Image(
                    painter = painterResource(id = R.drawable.lungs_icon),
                    contentDescription = "app_icon",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    "Бросаем курить!",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "Версия 1.0.0",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
                ClickableText(
                    text = buildAnnotatedString {
                        append("Отказ от ответственности")
                        addStyle(
                            style = SpanStyle(
                                color = Color.Blue,
                                textDecoration = TextDecoration.Underline
                            ),
                            start = 0,
                            end = this.length
                        )
                    },
                    onClick = { showDialog = true }
                )
            }
        }
        if (showDialog && disclaimer != null) {
            DisclaimerDialog(disclaimer = disclaimer, onDismiss = { showDialog = false })
        }
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier.padding(bottom = 26.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            InfoText()
        }
    }
}

@Composable
fun ClickableEmailText(email: String, uriHandler: UriHandler) {
    val annotatedString = buildAnnotatedString {
        append(email)
        addStyle(
            style = SpanStyle(
                color = Color.Blue,
                textDecoration = TextDecoration.Underline
            ),
            start = 0,
            end = email.length
        )
        addStringAnnotation(
            tag = "URL",
            annotation = "mailto:$email",
            start = 0,
            end = email.length
        )
    }

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        },
        style = TextStyle(
            fontSize = 14.sp
        )
    )
}

@Composable
fun DisclaimerDialog(disclaimer: Disclaimer, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Column {
                Text(
                    text = "Отказ\nот ответственности",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp
                )
                Divider(color = Color.Gray, thickness = 1.dp) // Полоска у нижней части заголовка
            }
        },
        text = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp) // Высота для прокручиваемого контента
                    .verticalScroll(rememberScrollState()),
            ) {
                Column {
                    disclaimer.content.forEach { paragraph ->
                        Text(
                            text = paragraph,
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontFamily = MyTextStyles.mRobotoFontFamily,
                            fontWeight = FontWeight.Normal,
                        )
                    }
                }
            }
        },
        confirmButton = {
            Column {
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp
                ) // Полоска у верхней части блока с кнопкой
                TextButton(onClick = onDismiss) {
                    Text(
                        "ОК",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    )
}