package ru.sashel007.quitsmoking.mainscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.Achievements
import ru.sashel007.quitsmoking.mainscreen.elements.Advices
import ru.sashel007.quitsmoking.mainscreen.elements.DontSmokeText
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
            DontSmokeText()
            SmokeTimer()
            ProgressLine()
            Timer()
            Spacer(modifier = Modifier.height(26.dp))
            Achievements()
            Spacer(modifier = Modifier.height(26.dp))
            Motivation()
            Spacer(modifier = Modifier.height(26.dp))
            Advices()
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
