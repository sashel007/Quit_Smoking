package ru.sashel007.quitsmoking.mainscreen.elements

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times

@Composable
fun Achievements() {
    Box(
        modifier = Modifier
            .width(360.dp)
            .height(188.dp)
            .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp, bottomStart = 14.dp, bottomEnd = 14.dp))
            .background(Color(red = 0.7372549f, green = 0.7372549f, blue = 0.9137255f, alpha = 1f))
            .padding(top = 10.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 2.dp), // You can adjust this value to reduce the space at the bottom of the Column
            verticalArrangement = Arrangement.Top // Align items to the top or use custom spacing
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 10.dp, end = 10.dp), // Adjust bottom padding to reduce space between this Row and LazyRow
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Достижения",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "Показать всё",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
            LazyRow(
                // You can control the spacing between items inside LazyRow with contentPadding and itemSpacing
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp), // Adjust horizontal padding for space around items
                horizontalArrangement = Arrangement.spacedBy(10.dp) // Adjust space between items
            ) {
                items(List(10) { "Achievement #${it + 1}" }) { achievement ->
                    AchievementBlock(achievement)
                }
            }
        }
    }
}

@Composable
fun AchievementBlock(achievement: String) {
    Box(
        modifier = Modifier
            .requiredWidth(95.dp)
            .requiredHeight(116.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(0xffd9d9d9)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = achievement,
            fontSize = 10.sp, // Adjust as needed
            fontWeight = FontWeight.Normal,
            color = Color.Black
        )
    }
}



