package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R

@Composable
fun Advices() {
    Box(
        modifier = Modifier
            .width(360.dp)
            .height(188.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 14.dp,
                    topEnd = 14.dp,
                    bottomStart = 14.dp,
                    bottomEnd = 14.dp
                )
            )
            .background(
                Color(
                    red = 255 / 255f,
                    green = 169 / 255f,
                    blue = 157 / 255f,
                    alpha = 255 / 255f
                )
            )
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.advices_title),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = stringResource(id = R.string.show_all),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
            LazyRow(
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(List(10) { "Motivation #${it + 1}" }) { adviceInfo ->
                    AdviceBlock(adviceInfo)
                }
            }
        }
    }
}

@Composable
fun AdviceBlock(adviceInfo: String) {
    Box(
        modifier = Modifier
            .requiredWidth(95.dp)
            .requiredHeight(116.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(0xffd9d9d9)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = adviceInfo,
            fontSize = 10.sp,
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}