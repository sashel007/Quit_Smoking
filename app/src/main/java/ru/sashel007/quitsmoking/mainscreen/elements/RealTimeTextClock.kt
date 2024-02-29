package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun RealTimeTextClock() {
    val currentTime by remember { mutableStateOf("") }

}