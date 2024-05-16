package ru.sashel007.quitsmoking.mainscreen.elements.settings

import androidx.compose.runtime.Composable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun LangsSettingItem(title: String) {
    Box() {
        LangsSettingItemImageButton(title)
    }
}

@Composable
fun LangsSettingItemImageButton(text: String) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .width(360.dp)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 6.dp)
            .clickable { showDialog = true },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.langs_settings_item_btn),
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

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    text = "Смена языков",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "Возможность смены языков скоро будет доступна. Следите за обновлениями",
                    fontFamily = MyTextStyles.mRobotoFontFamily,
                    fontWeight = FontWeight.Light,
                    fontSize = 18.sp
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text("ОК",
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
        )
    }
}