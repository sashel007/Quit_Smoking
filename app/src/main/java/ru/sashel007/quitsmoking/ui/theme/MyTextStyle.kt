package ru.sashel007.quitsmoking.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.AppColors.violette

object MyTextStyles {
    val mRobotoFontFamily = FontFamily(
        Font(R.font.roboto_black, FontWeight.Black),
        Font(R.font.roboto_bold, FontWeight.Bold),
        Font(R.font.roboto_light, FontWeight.Light),
        Font(R.font.roboto_medium, FontWeight.Medium),
        Font(R.font.roboto_regular, FontWeight.Normal)
    )

    val startingLittleTextStyle = TextStyle(
        color = violette, lineHeight = 16.sp, fontSize = 18.sp, fontFamily = mRobotoFontFamily
    )

    val buttonTextStyle = TextStyle(
        color = violette,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = mRobotoFontFamily

    )

    val bigButtonTextStyle = TextStyle(
        color = Color.White,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = mRobotoFontFamily
    )

    val numberButtonTextStyle = TextStyle(
        color = violette,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = mRobotoFontFamily
    )

    val summaryNumberTextStyle = TextStyle(
        color = Color.Black,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = mRobotoFontFamily
    )
}

object AppColors {
    val violette = Color(0xFF590D82)
}
