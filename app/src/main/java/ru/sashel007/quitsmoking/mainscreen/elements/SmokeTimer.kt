package ru.sashel007.quitsmoking.mainscreen.elements

import android.os.CountDownTimer
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import ru.sashel007.quitsmoking.R

@Composable
fun SmokeTimer() {
    var timeInSeconds by remember { mutableStateOf(0L) }
    var isTimerRunning by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    val timer = object : CountDownTimer(Long.MAX_VALUE, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            timeInSeconds++
        }

        override fun onFinish() {
            // Не будет вызвано, так как интервал установлен как Long.MAX_VALUE
        }
    }

    Column {
        Image(
            painter = painterResource(id = R.drawable.timer),
            contentDescription = "Not Smoking Icon",
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
                .align(
                    alignment = Alignment.CenterHorizontally
                ),
            contentScale = ContentScale.FillBounds
        )
        Row {
            Button(onClick = {
                if (isTimerRunning) {
                    timer.cancel()
                } else {
                    timer.start()
                }
                isTimerRunning = !isTimerRunning
            }) {
                Text(if (isTimerRunning) "Stop" else "Start")
            }

            Text(
                text = "${timeInSeconds / 3600}h : ${(timeInSeconds % 3600) / 60}m : ${timeInSeconds % 60}s",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}