package ru.sashel007.quitsmoking.navigator

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

@Composable
fun FirstMonthWithoutSmokingPage(function: () -> Unit) {
    val userViewModel: UserViewModel = viewModel()
    val currentUserData by userViewModel.userData.observeAsState()
    val cigarettesCount = currentUserData?.cigarettesPerDay ?: 0
    val packCost = currentUserData?.packCost ?: 0
    Log.d("FirstMonthWithoutSmokingPage", "$cigarettesCount + $currentUserData")
    val nonSmokedCigarettes = cigarettesCount * 30
    val cigarettesInPack = 20
    val daysInMonth = 30
    val monthMoneySpent = packCost / cigarettesInPack * nonSmokedCigarettes * daysInMonth

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "Ваш первый месяц без курения", fontSize = 20.sp, modifier = Modifier.padding(bottom = 24.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cigarette_smoked), // Replace with your image
                        contentDescription = "Image 1",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "$nonSmokedCigarettes", modifier = Modifier.weight(1f))
                    Text(
                        text = "невыкуренных сигарет",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp, // или другой размер, который вам подходит
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp // или другой размер, который вам подходит
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(150.dp)
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.rubble), // Replace with your image
                        contentDescription = "Image 2",
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "$monthMoneySpent")
                    Text(
                        text = "потрачено рублей",
                        modifier = Modifier.weight(1f),
                        fontSize = 14.sp, // или другой размер, который вам подходит
                        textAlign = TextAlign.Center,
                        lineHeight = 16.sp // или другой размер, который вам подходит
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "по данный ВОЗ улучшатся 4 разных аспекта вашего тела", fontSize = 18.sp)

        Button(
            onClick = { function() },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 2.dp, top = 5.dp, end = 2.dp, bottom = 2.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Дальше",
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}
