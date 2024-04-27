package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R

@Composable
fun MyAppBar(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 10.dp, end = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.dont_smoke_title),
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .padding(start = 8.dp)
        )
        Spacer(Modifier.weight(1f))
        Image(
            painter = painterResource(id = R.drawable.quitesmoke_share_icon),
            contentDescription = stringResource(id = R.string.share),
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(22.dp))
        Image(
            painter = painterResource(id = R.drawable.settings_button_1),
            contentDescription = stringResource(id = R.string.settings),
            modifier = Modifier.size(28.dp).clickable {
                navController.navigate("settings")
            }
        )
    }

}