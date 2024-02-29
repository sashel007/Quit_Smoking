package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R

@Composable
fun BackButtonImage(navController: NavController) {
    Box(modifier = Modifier.wrapContentSize()) {
        Image(
            painter = painterResource(id = R.drawable.back_button),
            contentDescription = stringResource(R.string.back_button),
            modifier = Modifier
                .size(32.dp) // Размер кнопки
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}
