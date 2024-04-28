package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun DailyAdvice() {
    val colorStops = arrayOf(
        0.0f to Color(0xFFC5CAE9), 0.2f to Color(0xFFB0BCE2), 1f to Color(0xFFE6E9FF)
    )

    Box(modifier = Modifier
        .size(160.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { }
        .background(Brush.linearGradient(colorStops = colorStops))
        .padding(8.dp)) {
        Text(
            "Совет дня",
            fontFamily = MyTextStyles.mRobotoFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 6.dp, top = 12.dp)
        )
        Image(painter = painterResource(id = R.drawable.advice_icon),
            contentDescription = "Как ваше настроение",
            modifier = Modifier
                .size(90.dp)
                .align(Alignment.BottomEnd)
                .graphicsLayer {
                    translationY = 16.dp.toPx()
                    translationX = 16.dp.toPx()
                },
            contentScale = ContentScale.Crop)
    }
}