package ru.sashel007.quitsmoking.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.DontSmokeText
import ru.sashel007.quitsmoking.mainscreen.elements.ProgressLine
import ru.sashel007.quitsmoking.mainscreen.elements.SmokeTimer
import ru.sashel007.quitsmoking.mainscreen.elements.Timer

@Composable
fun MainScreen() {
    val imageHeight = LocalDensity.current.run { 20.dp }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            DontSmokeText()
            SmokeTimer()
            ProgressLine()
            Timer()
        }
    }
}