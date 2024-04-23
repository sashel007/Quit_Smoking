package ru.sashel007.quitsmoking.mainscreen.elements

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.SmokingStats
import ru.sashel007.quitsmoking.viewmodel.SmokingStatsViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Timer(smokingStats: androidx.compose.runtime.State<SmokingStats?>) {
    val days by remember(smokingStats.value?.days) {
        derivedStateOf {
            smokingStats.value?.days.toString()
        }
    }

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
                    text = days,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp,
                    color = Color(0xFF590D82)
                )
                Text(
                    text = stringResource(R.string.days_),
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
// aAnYPOr0XzNY4zr0GpD4
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,  // Center items vertically
                modifier = Modifier.fillMaxHeight()
            ) {
                Text(
                    text = smokingStats.value?.hours.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp,
                    color = Color(0xFF590D82)
                )
                Text(
                    text = stringResource(R.string.hours_),
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
                    text = smokingStats.value?.minutes.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp,
                    color = Color(0xFF590D82)
                )
                Text(
                    text = stringResource(R.string.minutes_),
                    fontSize = 18.sp,
                    color = Color(0xFF590D82)
                )
            }
        }
    }
}


