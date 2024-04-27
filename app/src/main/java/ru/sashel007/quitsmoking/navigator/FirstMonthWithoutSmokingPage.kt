package ru.sashel007.quitsmoking.navigator

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.mainscreen.elements.BackButtonImage
import ru.sashel007.quitsmoking.ui.theme.AppColors.violette
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles.buttonTextStyle
import ru.sashel007.quitsmoking.ui.theme.MyTextStyles.summaryNumberTextStyle
import ru.sashel007.quitsmoking.viewmodel.UserViewModel


/** PAGE: Первый месяц без курения */
@Composable
fun FirstMonthWithoutSmokingPage(
    navController: NavController,
    userViewModel: UserViewModel,
    onClickForward: () -> Unit
) {
    userViewModel.loadDataForFirstMonthStats()
    val currentUserData = userViewModel.userData.observeAsState().value
    Log.d("FirstMonthWithoutSmokingPage.kt", "val currentUserData: $currentUserData")
    val cigarettesPerDay = currentUserData?.cigarettesPerDay ?: 1
    Log.d("FirstMonthWithoutSmokingPage", "$cigarettesPerDay")
    val packCost = currentUserData?.packCost ?: 1
    val nonSmokedCigarettes = cigarettesPerDay * 30
    val cigarettesInPack = currentUserData?.cigarettesInPack ?: 1
    val daysInMonth = 30
    val daysForOnePack = if (cigarettesPerDay > 0) cigarettesInPack / cigarettesPerDay else 1
    val packsForMonth = if (cigarettesPerDay > 0) daysInMonth / daysForOnePack else 1
    val monthMoneySaved = packCost * packsForMonth
    val buttonSize = 32.dp

    Log.d(
        "FirstMonthWithoutSmokingPage", "packCost = $packCost, cigarettesInPack = $cigarettesInPack\n" +
                "nonSmokedCigarettes = $nonSmokedCigarettes, daysInMonth = $daysInMonth"
    )
    Log.d("FirstMonthWithoutSmokingPage", "monthMoneySpent = $monthMoneySaved")

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {

            /** КНОПКА "НАЗАД" */

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                BackButtonImage(navController = navController, buttonSize)
            }
            Spacer(modifier = Modifier.height(88.dp))

            /** ТЕКСТ */

            Text(
                text = stringResource(id = R.string.first_month_non_smoke),
                color = violette,
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            /** ИТОГ */

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(260.dp)
                        .width(150.dp)
                        .border(2.dp, violette, RoundedCornerShape(10.dp))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.cigarettes_smoked_summary),
                            contentDescription = "Image 1",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$nonSmokedCigarettes",
                            modifier = Modifier.weight(1f),
                            style = summaryNumberTextStyle
                        )
                        Text(
                            text = stringResource(R.string.non_smoked_cigarettes_summary),
                            modifier = Modifier.weight(1.5f),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .height(260.dp)
                        .width(150.dp)
                        .border(2.dp, violette, RoundedCornerShape(10.dp))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.money_spent_summary),
                            contentDescription = "Image 2",
                            modifier = Modifier
                                .size(120.dp)
                                .padding(top = 10.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$monthMoneySaved",
                            modifier = Modifier.weight(1f),
                            style = summaryNumberTextStyle
                        )
                        Text(
                            text = stringResource(R.string.spent_money_summary),
                            modifier = Modifier.weight(1.5f),
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            /** ПО ДАННЫМ ВОЗ... */

            Box(modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = stringResource(R.string.un_data),
                        fontSize = 18.sp
                    )
                }
            }

            Button(
                onClick = { onClickForward() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(start = 14.dp, top = 5.dp, end = 2.dp, bottom = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.next),
                    fontSize = 20.sp,
                    color = Color.White
                )
            }
        }
    }
}
