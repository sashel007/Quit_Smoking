package ru.sashel007.quitsmoking.navigator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(currentPage: Int, numberOfPages: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        for (i in 0 until numberOfPages) {
            Spacer(modifier = Modifier.width(4.dp).padding(15.dp))
            Box(
                modifier = Modifier.size(8.dp)
                    .background(
                        if (i == currentPage) Color.Black else Color.Gray,
                        shape = CircleShape
                    )
            )
        }
    }
}
