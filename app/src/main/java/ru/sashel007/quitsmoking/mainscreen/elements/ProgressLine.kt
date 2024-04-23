package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.data.repository.dto.SmokingStats

@Composable
fun ProgressLine(smokingStats: State<SmokingStats?>) {
    var expanded by remember { mutableStateOf(false) }
    val modifierAnimated = Modifier
        .padding(bottom = 20.dp)
        .animateContentSize(animationSpec = tween(durationMillis = 800))
    var daysAfterCancelling = smokingStats.value?.days
    var nonSmokedCigarettes = smokingStats.value?.nonSmokedCigarettes
    var moneySaved = smokingStats.value?.savedMoney
    var timeSaved = smokingStats.value?.savedTimeInMinutes

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .shadow(5.dp, RoundedCornerShape(26.dp))
        .clip(RoundedCornerShape(26.dp))
        .background(Color.White)
        .clickable { expanded = !expanded }) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BlockText()
            ExpandText(expanded)
        }
        if (expanded) {
            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(Modifier.weight(1f))
                    BoxElement {
                        DaysPassed(
                            Modifier
                                .width(140.dp)
                                .height(100.dp),
                            daysAfterCancelling
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    BoxElement {
                        NoCigarettesSmoked(
                            Modifier
                                .width(140.dp)
                                .height(100.dp),
                            nonSmokedCigarettes
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
                Spacer(Modifier.height(14.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, bottom = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(Modifier.weight(1f))
                    BoxElement {
                        MoneySaved(
                            Modifier
                                .width(140.dp)
                                .height(100.dp),
                            moneySaved
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    BoxElement {
                        TimeSaved(
                            Modifier
                                .width(140.dp)
                                .height(100.dp),
                            timeSaved
                        )
                    }
                    Spacer(Modifier.weight(1f))
                }
            }
        } else {
            Row(modifier = modifierAnimated) {
                DaysPassed(
                    Modifier
                        .weight(1f)
                        .padding(8.dp),
                    daysAfterCancelling
                )
                NoCigarettesSmoked(
                    Modifier
                        .weight(1f)
                        .padding(8.dp),
                    nonSmokedCigarettes
                )
                MoneySaved(
                    Modifier
                        .weight(1f)
                        .padding(8.dp),
                    moneySaved
                )
                TimeSaved(
                    Modifier
                        .weight(1f)
                        .padding(8.dp),
                    timeSaved
                )
            }
        }
    }
}

@Composable
fun BoxElement(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .shadow(4.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
//            .border(2.dp, Color.Black, RoundedCornerShape(10.dp))
            .background(Color(0xFFFFF2E1))
            .padding(8.dp),

        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Composable
fun BlockText() {
    Text(
        text = stringResource(R.string.common_progress),
        fontSize = 20.sp,
        fontWeight = Bold,
        textAlign = TextAlign.Left,
        modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 16.dp)

    )
}

@Composable
fun DaysPassed(modifier: Modifier, daysAfterCancelling: Long?) {
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
            text = daysAfterCancelling.toString(), fontSize = 18.sp, fontWeight = Bold
        )
        Text(
            text = stringResource(R.string.days_after_cancelling),
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
fun NoCigarettesSmoked(modifier: Modifier, nonSmokedCigs: Int?) {
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
            text = nonSmokedCigs.toString(), fontSize = 18.sp, fontWeight = Bold
        )
        Text(
            text = stringResource(R.string.smoked_cigarettes),
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
fun MoneySaved(modifier: Modifier, moneySaved: Int?) {
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
            text = "$moneySaved руб.", fontSize = 18.sp, fontWeight = Bold
        )
        Text(
            text = stringResource(R.string.money_saved),
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
fun TimeSaved(modifier: Modifier, timeSaved: Int?) {
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
            text = "$timeSaved д.", fontSize = 18.sp, fontWeight = Bold
        )
        Text(
            text = stringResource(R.string.time_saved),
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
fun ExpandText(expanded: Boolean) {
    Text(
        text = if (expanded) stringResource(R.string.collapse_box) else stringResource(R.string.more_details),
        fontSize = 14.sp,
        textAlign = TextAlign.Right,
        modifier = Modifier.padding(start = 16.dp, top = 10.dp, bottom = 16.dp, end = 16.dp)
    )
}