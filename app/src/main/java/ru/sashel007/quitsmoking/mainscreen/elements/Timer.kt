package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Timer() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 35.dp)
            .shadow(5.dp, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(10.dp)),  // Then clip
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(90.dp)
                .background(Color(0xFFCCA8E9))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,  // Center items vertically
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "9",
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp,
                    color = Color(0xFF590D82)
                )
                Text(
                    text = "дней",
                    fontSize = 18.sp,
                    color = Color(0xFF590D82)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFC3BEF0))
                .height(90.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,  // Center items vertically
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "5",
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp,
                    color = Color(0xFF590D82)
                )
                Text(
                    text = "часов",
                    fontSize = 18.sp,
                    color = Color(0xFF590D82)
                )
            }
        }

        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color(0xFFCADEFC))
                .height(90.dp)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,  // Center items vertically
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = "20",
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp,
                    color = Color(0xFF590D82)
                )
                Text(
                    text = "минут",
                    fontSize = 18.sp,
                    color = Color(0xFF590D82)
                )
            }
        }
    }
}


