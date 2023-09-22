package ru.sashel007.quitsmoking.navigator

import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import ru.sashel007.quitsmoking.R
import java.util.Locale

@Composable
fun QuitDateSelectionPage(navController: NavController, onContinueClicked: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(10.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 70.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "Когда вы бросили курить?",
                    fontWeight = FontWeight.Bold,
                    fontSize = 33.sp,
                    modifier = Modifier.padding(vertical = 8.dp),
                    textAlign = TextAlign.Center,
                    color = Color(0xFF590D82)
                )
            }

            Text(
                text = "Если вы пока не бросили, выберите, когда собираетесь бросить",
                maxLines = 2,
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center,
                lineHeight = 16.sp,
                color = Color(0xFF590D82)
            )

            // Display current date and time in the desired format
            val currentDate = LocalDate.now()
            val currentTime = LocalTime.now()
            val russianLocale = Locale("ru")
            val dateFormatter = DateTimeFormatter.ofPattern("dd MMMM",russianLocale)
            val timeFormatter = DateTimeFormatter.ofPattern("HH:mm",russianLocale)
            Text(
                text = "${currentDate.format(dateFormatter)} в ${currentTime.format(timeFormatter)}",
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF590D82)
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { /* Open Date Picker */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                ) {
                    Text(text = "Выбрать дату", color = Color(0xFF590D82))
                }
                Button(
                    onClick = { /* Open Time Picker */ },
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier.border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                ) {
                    Text("Выбрать время", color = Color(0xFF590D82))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Dummy Image for now, replace with your desired image
            Image(
                modifier = Modifier
                    .size(120.dp),
                painter = painterResource(id = R.drawable.calendar_second),
                contentDescription = "Your Image Description",
                )

            Spacer(modifier = Modifier.height(90.dp))

            Button(
                onClick = { onContinueClicked() },
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
}

