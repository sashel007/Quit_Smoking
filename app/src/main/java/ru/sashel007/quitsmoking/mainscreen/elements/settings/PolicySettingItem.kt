package ru.sashel007.quitsmoking.mainscreen.elements.settings

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles


@Composable
fun PolicySettingItem(title: String) {
    Box() {
        PolicySettingItemImageButton(title)
    }
}

@Composable
fun PolicySettingItemImageButton(text: String) {
    Box(
        modifier = Modifier
            .width(360.dp)
            .padding(start = 24.dp, end = 24.dp, top = 12.dp, bottom = 6.dp)
            .clickable {},
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