package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles
import ru.sashel007.quitsmoking.util.getTipsFromJson
import java.util.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyTip() {
    val colorStops = arrayOf(
        0.0f to Color(0xFFC5CAE9), 0.2f to Color(0xFFB0BCE2), 1f to Color(0xFFE6E9FF)
    )
    val onShowTipDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(modifier = Modifier
        .size(160.dp)
        .clip(RoundedCornerShape(16.dp))
        .clickable { onShowTipDialog.value = true }
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

    if (onShowTipDialog.value) {
        val tipList = remember {
            getTipsFromJson(context)
        }
        val random = Random()
        val randomIndex = random.nextInt(tipList.size)
        val randomTip = tipList[randomIndex]
        val randomTipTitle = randomTip.name
        val randomTipDescription = randomTip.text

        BasicAlertDialog(onDismissRequest = { onShowTipDialog.value = false }) {
            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .width(100.dp)
                    .background(Color.White, RoundedCornerShape(12.dp))
            ) {
                Box(modifier = Modifier.align(Alignment.TopEnd)) {
                    IconButton(onClick = { onShowTipDialog.value = false }) {
                        Icon(Icons.Default.Clear, contentDescription = "Назад")
                    }
                }
                Column(modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 16.dp, vertical = 22.dp)) {
                    Text(
                        text = randomTipTitle,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = randomTipDescription,
                        fontFamily = MyTextStyles.mRobotoFontFamily,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start,
                        lineHeight = 18.sp,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}