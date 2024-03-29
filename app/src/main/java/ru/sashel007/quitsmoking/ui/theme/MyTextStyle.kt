package ru.sashel007.quitsmoking.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.ui.theme.AppColors.violette

@Composable
fun MyTextStyle() {
    TextStyle(
        color = violette,
        lineHeight = 16.sp
    )
}

object MyTextStyles {
    val startingLittleTextStyle = TextStyle(
        color = violette,
        lineHeight = 16.sp,
        fontSize = 18.sp
    )

    val buttonTextStyle = TextStyle(
        color = violette,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold
    )

    val bigButtonTextStyle = TextStyle(
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )

    val numberButtonTextStyle = TextStyle(
        color = violette,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )

    val summaryNumberTextStyle = TextStyle(
        color = Color.Black,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )
}

object AppColors {
    val violette = Color(0xFF590D82)
}