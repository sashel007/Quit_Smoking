package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun MyAppBar() {
    var showPopup by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.dont_smoke_title),
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 8.dp)
        )
        Spacer(Modifier.weight(1f))
        IconWithTooltip(
            painterResource(id = R.drawable.quitsmoke_share_icon),
            contentDescription = stringResource(id = R.string.share),
            showPopup,
            { showPopup = true },
            { showPopup = false }
        )
//        Image(
//            painter = painterResource(id = R.drawable.quitsmoke_share_icon),
//            contentDescription = stringResource(id = R.string.share),
//            modifier = Modifier
//                .size(22.dp)
//                .padding(end = 1.dp)
//                .clickable { showPopup = true }
//        )

    }

}

@Composable
fun IconWithTooltip(
    painter: Painter,
    contentDescription: String,
    showTooltip: Boolean,
    onIconClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Box {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier
                .size(22.dp)
                .clickable(onClick = onIconClick)
        )

        if (showTooltip) {
            Popup(
                alignment = Alignment.Center,
                offset = IntOffset(0, 60), // Смещение, чтобы расположить тултип ниже иконки
                onDismissRequest = onDismissRequest,
                properties = PopupProperties(focusable = true)
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.Black.copy(alpha = 0.8f), RoundedCornerShape(8.dp))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Функция выгрузки данных скоро будет доступна",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}