package ru.sashel007.quitsmoking.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import ru.sashel007.quitsmoking.mainscreen.elements.LicencedText
import ru.sashel007.quitsmoking.mainscreen.elements.Motivation
import ru.sashel007.quitsmoking.mainscreen.elements.MyAppBar
import ru.sashel007.quitsmoking.mainscreen.elements.ProgressLine
import ru.sashel007.quitsmoking.mainscreen.elements.Timer

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .border(2.dp, Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Column {

            /** Область с таймером */

            Box(
                modifier = Modifier
//                    .align(Alignment.TopCenter)
                    .border(2.dp, Color.Red)
//            .verticalScroll(rememberScrollState()),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .border(2.dp, Color.Red)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFEDB9FF), // верхний цвет - #EDB9FF
                                    Color(0xFF7FBDF6).copy(alpha = 0.8f)  // нижний цвет - #7FBDF6 с прозрачностью 80%
                                )
                            )
                        )
                ) {
                    MyAppBar(Modifier.border(2.dp, Color.Red))
                    Timer()
                    Spacer(modifier = Modifier.height(20.dp))
                    ProgressLine()
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            /** Область с белым фоном */

            Box(
                modifier = Modifier
//                    .align(Alignment.BottomCenter)
                    .padding(top = 40.dp)
                    .border(2.dp, Color.Red)
                    .clip(RoundedCornerShape(26.dp, 26.dp))
                    .offset(y = (-50).dp)

            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .border(2.dp, Color.Red)
//                        .background(
//                            Color(
//                                red = 0.9310132f,
//                                green = 0.9310132f,
//                                blue = 0.9310132f,
//                                alpha = 1f
//                            )
//                        )
                ) {
//                    Spacer(modifier = Modifier
////                        .height(26.dp)
//                        .border(2.dp, Color.Red))
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
}