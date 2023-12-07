package ru.sashel007.quitsmoking.navigator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.R


@Composable
fun StartingPage(onClickAction: () -> Unit) {
    val customColor = Color(0xFFDAC6F1)

    Box(
        modifier = Modifier.fillMaxSize().background(color = Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.cat),
                contentDescription = "Your Image Description",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(150.dp) // Adjust size as per your requirements
            )

            Spacer(modifier = Modifier.height(16.dp)) // Adjust spacing as per your requirements

            Text(
                text = "Добро пожаловать в",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(4.dp),
            )
            Text(
                text = "STOP SMOKING",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp)) // Adjust spacing as per your requirements

            Button(
                onClick = {
                    onClickAction()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Запустить",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp)) // Adjust spacing as per your requirements

            Button(
                onClick = {
                    onClickAction()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = customColor,
                        contentColor = Color.White
                    )
            ) {
                Text(
                    text = "Войти",
                    fontSize = 20.sp,
                    color = Color.Black
                )
            }
        }
    }
}
