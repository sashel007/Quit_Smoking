package ru.sashel007.quitsmoking.mainscreen.elements

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusProperties

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