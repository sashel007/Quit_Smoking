package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DontSmokeText() {
    Text(
        text = "Я НЕ КУРЮ",
        fontWeight = FontWeight.Bold,
        fontSize = 23.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier.fillMaxWidth().padding(15.dp)
    )
}