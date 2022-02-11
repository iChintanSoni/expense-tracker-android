package dev.chintansoni.expensetracker.ui.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithError(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit,
    errorText: String,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            leadingIcon = leadingIcon,
            isError = errorText.isNotEmpty(),
            onValueChange = onValueChange,
            singleLine = singleLine,
            label = { Text(label) },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
        AnimatedVisibility(visible = errorText.isNotEmpty()) {
            Text(
                text = errorText,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}