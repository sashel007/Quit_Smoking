package ru.sashel007.quitsmoking.navigator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ru.sashel007.quitsmoking.viewmodel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CigarettesInPackPage(onNextClicked: () -> Unit) {
    var cigarettesInPack by remember { mutableStateOf("0") }
    val userViewModel: UserViewModel = viewModel()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(text = "сколько сигарет было в одной пачке?")

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = cigarettesInPack,
            onValueChange = { newValue ->
                cigarettesInPack = if (cigarettesInPack == "0") {
                    newValue
                } else {
                    cigarettesInPack + newValue
                }
            },
            label = { Text("Cigarettes in Pack") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val count = cigarettesInPack.toInt()
            userViewModel.updateCigarettesInPack(count)
            onNextClicked()
        }) {
            Text(text = "дальше")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Calculator Keypad
        Column {
            (0..9).chunked(3).forEach { row ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    row.forEach { number ->
                        Button(
                            modifier = Modifier.weight(1f, fill = true),
                            onClick = {
                                cigarettesInPack = if (cigarettesInPack == "0") {
                                    number.toString()
                                } else {
                                    cigarettesInPack + number.toString()
                                }
                            }
                        ) {
                            Text(text = number.toString())
                        }
                    }
                }
            }

            // "<-" button to delete one character
            Button(
                onClick = {
                    if (cigarettesInPack.length > 1) {
                        cigarettesInPack = cigarettesInPack.dropLast(1)
                    } else {
                        cigarettesInPack = "0"
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "<-")
            }
        }
    }
}
