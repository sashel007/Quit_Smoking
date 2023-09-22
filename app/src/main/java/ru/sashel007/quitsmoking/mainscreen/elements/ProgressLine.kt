package ru.sashel007.quitsmoking.mainscreen.elements

import android.util.Half.toFloat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.ExtraBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R

@Composable
fun ProgressLine(modifier: Modifier = Modifier.padding(bottom = 20.dp)) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(5.dp, RoundedCornerShape(6.dp))
            .clip(RoundedCornerShape(6.dp))
            .background(Color.White)
    ) {
        BlockText()
        Row()
        {
            DaysPassed(
                Modifier
                    .weight(1f)
                    .padding(8.dp))
            NoCigarettesSmoked(
                Modifier
                    .weight(1f)
                    .padding(8.dp))
            MoneySaved(
                Modifier
                    .weight(1f)
                    .padding(6.dp))
            TimeSaved(
                Modifier
                    .weight(1f)
                    .padding(8.dp))
        }
    }
}

@Composable
fun BlockText() {
    Text(
        text = "Общий прогресс",
        fontSize = 20.sp,
        fontWeight = Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
    )
}

@Composable
fun DaysPassed(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.calendar),
            contentDescription = null,
            modifier = Modifier
                .width(35.dp)
                .height(35.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "17",
            fontSize = 14.sp,
            fontWeight = Bold
        )
        Text(
            text = "дней после отказа",
            fontSize = 12.sp,
            textAlign = TextAlign.Center, // This centers the text
            maxLines = 2,
            overflow = TextOverflow.Clip,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 14.sp
        )
    }
}

@Composable
fun NoCigarettesSmoked(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.cigarette_smoked),
            contentDescription = null,
            modifier = Modifier
                .width(35.dp)
                .height(35.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "244",
            fontSize = 14.sp,
            fontWeight = Bold
        )
        Text(
            text = "выкуренных сигарет",
            fontSize = 12.sp,
            textAlign = TextAlign.Center, // This centers the text
            maxLines = 2,
            overflow = TextOverflow.Clip,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 14.sp
        )
    }
}

@Composable
fun MoneySaved(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.rubble),
            contentDescription = null,
            modifier = Modifier
                .width(35.dp)
                .height(35.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "60 руб.",
            fontSize = 14.sp,
            fontWeight = Bold
        )
        Text(
            text = "сэкономлено денег",
            fontSize = 12.sp,
            textAlign = TextAlign.Center, // This centers the text
            maxLines = 2,
            overflow = TextOverflow.Clip,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 14.sp
        )
    }
}

@Composable
fun TimeSaved(modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.clockwork),
            contentDescription = null,
            modifier = Modifier
                .width(35.dp)
                .height(35.dp),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = "1 д.",
            fontSize = 14.sp,
            fontWeight = Bold
        )
        Text(
            text = "экономия времени",
            fontSize = 12.sp,
            textAlign = TextAlign.Center, // This centers the text
            maxLines = 2,
            overflow = TextOverflow.Clip,
            modifier = Modifier.fillMaxWidth(),
            lineHeight = 14.sp
        )
    }
}