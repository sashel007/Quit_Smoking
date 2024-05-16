package ru.sashel007.quitsmoking.mainscreen.elements.settings

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.mainscreen.elements.InfoText
import ru.sashel007.quitsmoking.mainscreen.elements.NavRoutes
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun MailToDevSettingItem(title: String, navController: NavController) {
    Box() {
        MailToDevSettingItemImageButton(navController, title)
    }
}

@Composable
fun MailToDevSettingItemImageButton(navController: NavController, text: String) {
    Box(
        modifier = Modifier
            .width(360.dp)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 6.dp)
            .clickable { navController.navigate(NavRoutes.MAIL_TO_DEV_INFO_PAGE) },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.mail_to_dev_settings_item_btn),
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
fun MailToDevInfoPage(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .scrollable(rememberScrollState(), orientation = Orientation.Vertical),
        verticalArrangement = Arrangement.Top
    ) {
        val backButtonSize = 26.dp
        val uriHandler = LocalUriHandler.current

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
                    "Сведения о разработчике",
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

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
        ) {
            Column {
                val imgList = listOf(
                    R.drawable.mail_icon,
                    R.drawable.github_icon,
                    R.drawable.vk_icon,
                    R.drawable.tg_icon
                )
                Text(
                    text = "Связаться с разработчиком",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Если у вас есть вопросы или предложения, вы можете связаться с разработчиком через следующие каналы:",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    lineHeight = 18.sp
                )
                Column(
                    modifier = Modifier.padding(start = 40.dp, top = 20.dp, bottom = 20.dp)
                ) {
                    ContactLink(
                        "i@sashel007.ru",
                        "mailto:i@sashel007.ru",
                        uriHandler,
                        imgList[0]
                    )
                    ContactLink(
                        "github.com/sashel007",
                        "https://github.com/sashel007",
                        uriHandler,
                        imgList[1]
                    )
                    ContactLink(
                        "vk.com/sashel007",
                        "https://vk.com/sashel007",
                        uriHandler,
                        imgList[2]
                    )
                    ContactLink(
                        "@mobile_dev_msc",
                        "https://t.me/mobile_dev_msc",
                        uriHandler,
                        imgList[3]
                    )
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

@Composable
fun ContactLink(text: String, url: String, uriHandler: UriHandler, icon: Int) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(bottom = 8.dp)
    ) {
        Image(
            painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .padding(end = 8.dp)
        )
        ClickableText(
            text = AnnotatedString(text),
            style = LocalTextStyle.current.copy(
                color = Color.Blue,
                fontSize = 16.sp,
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Normal
            ),
            onClick = {
                uriHandler.openUri(url)
            },
        )
    }
}

