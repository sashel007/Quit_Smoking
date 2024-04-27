package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.filter
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.settings.CancellingSettingItem
import ru.sashel007.quitsmoking.mainscreen.elements.settings.LangsSettingItem
import ru.sashel007.quitsmoking.mainscreen.elements.settings.MailToDevSettingItem
import ru.sashel007.quitsmoking.mainscreen.elements.settings.OurCommunitySettingItem
import ru.sashel007.quitsmoking.mainscreen.elements.settings.PolicySettingItem
import ru.sashel007.quitsmoking.mainscreen.elements.settings.SmokingDataSettingItem
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles

@Composable
fun AppSettings(navController: NavController) {
    val buttonSize = 28.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Box(modifier = Modifier.align(Alignment.TopStart)) {
                BackButtonImage(navController = navController, buttonSize)
            }
            Box(modifier = Modifier.align(Alignment.Center)) {
                Image(
                    painter = painterResource(id = R.drawable.settings_meme_img1),
                    contentDescription = stringResource(R.string.settings_meme_img),
                    modifier = Modifier
                        .size(120.dp)
                        .padding(top = 20.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.settings),
                fontFamily = MyTextStyles.mRobotoFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }

        Column {
            CancellingSettingItem("Изменить дату отмены", navController)
            SmokingDataSettingItem("Изменить данные о курении")
            MailToDevSettingItem("Связаться с разработчиком")
            OurCommunitySettingItem("Наше сообщество")
            LangsSettingItem("Языки")
            PolicySettingItem("Политика конфиденциальности")
            InfoText()
        }
    }


}

@Composable
fun InfoText() {
    val licencedTextSize = 10.sp
    val licencesTextLineHeight = 14.sp
    LicencedText(licencedTextSize, licencesTextLineHeight, Modifier.padding(top = 30.dp))
}

@Composable
fun SettingsMainImage() {
    Box() {
//        Image(
//            painter = painterResource(id = R.drawable.back_button),
//            contentDescription = stringResource(R.string.back_button),
//            modifier = Modifier
//                .size(32.dp)
//        )
    }
}