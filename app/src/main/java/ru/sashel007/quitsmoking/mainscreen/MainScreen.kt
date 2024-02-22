package ru.sashel007.quitsmoking.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.sashel007.quitsmoking.mainscreen.elements.Achievements
import ru.sashel007.quitsmoking.mainscreen.elements.Advices
import ru.sashel007.quitsmoking.mainscreen.elements.DontSmokeText
import ru.sashel007.quitsmoking.mainscreen.elements.LicencedText
import ru.sashel007.quitsmoking.mainscreen.elements.Motivation
import ru.sashel007.quitsmoking.mainscreen.elements.ProgressLine
import ru.sashel007.quitsmoking.mainscreen.elements.SmokeTimer
import ru.sashel007.quitsmoking.mainscreen.elements.Timer

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFEDB9FF), // верхний цвет - #EDB9FF
                                Color(0xFF7FBDF6).copy(alpha = 0.8f)  // нижний цвет - #7FBDF6 с прозрачностью 80%
                            )
                        )
                    )
            ) {
                DontSmokeText()
                SmokeTimer()
                Spacer(modifier = Modifier.height(20.dp))
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .clip(RoundedCornerShape(26.dp, 26.dp))
                    .background(
                        Color(
                            red = 0.9310132f,
                            green = 0.9310132f,
                            blue = 0.9310132f,
                            alpha = 1f
                        )
                    )
            ) {
                ProgressLine()
                Timer()
                Spacer(modifier = Modifier.height(26.dp))
                Achievements()
                Spacer(modifier = Modifier.height(26.dp))
                Motivation()
                Spacer(modifier = Modifier.height(26.dp))
                Advices()
                Spacer(modifier = Modifier.height(8.dp))
                LicencedText()
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}