package ru.sashel007.quitsmoking.mainscreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.drag
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.dp
import ru.sashel007.quitsmoking.mainscreen.elements.Achievements
import ru.sashel007.quitsmoking.mainscreen.elements.Advices
import ru.sashel007.quitsmoking.mainscreen.elements.LicencedText
import ru.sashel007.quitsmoking.mainscreen.elements.Motivation
import ru.sashel007.quitsmoking.mainscreen.elements.MyAppBar
import ru.sashel007.quitsmoking.mainscreen.elements.ProgressLine
import ru.sashel007.quitsmoking.mainscreen.elements.Timer

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
//    val backgroundColor by animateColorAsState(
//        targetValue = if (scrollState.firstVisibleItemScrollOffset > 0) Color.White else Color.Transparent,
//        animationSpec = tween(durationMillis = 300) // Плавная анимация за 300 мс
//    )
    val isScrolled by remember {
        derivedStateOf {
            scrollState.firstVisibleItemScrollOffset > 0
        }
    }
// Анимация изменения размера и цвета
    val appBarSize by animateDpAsState(
        targetValue = if (isScrolled) 48.dp else 56.dp,
        label = ""
    )
    val appBarColor by animateColorAsState(
        targetValue = if (isScrolled) Color.White else Color.LightGray,
        label = ""
    )

    LazyColumn(
        state = scrollState,
//        modifier = Modifier.background(Color(0xFFEDB9FF))
    ) {

//        stickyHeader {
//            Box() {
//                MyAppBar(isScrolled)
//            }
//        }

        item {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                /** Область с таймером */

                Box {
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
                        MyAppBar(isScrolled = isScrolled)
                        Spacer(Modifier.height(48.dp))
                        Timer()
                        Spacer(modifier = Modifier.height(20.dp))
                        ProgressLine()
                        Spacer(modifier = Modifier.height(36.dp))
                    }
                    Box(
                        modifier = Modifier
                            .height(20.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(32.dp, 32.dp))
                            .background(Color.White)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }

        item {
            /** Область с белым фоном */

            Box(
                modifier = Modifier
                    .padding(top = 2.dp)
                    .clip(RoundedCornerShape(26.dp, 26.dp))
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

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

