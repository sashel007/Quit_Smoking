package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        modifier = Modifier.focusProperties { canFocus = false }
    )

}