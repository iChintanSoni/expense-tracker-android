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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun TextFieldWithError(
    modifier: Modifier = Modifier,
    value: String = "",
    label: String = "",
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
    onValueChange: (String) -> Unit = {},
    errorText: String? = null,
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    Column {
        OutlinedTextField(
            modifier = modifier,
            value = value,
            leadingIcon = leadingIcon,
            enabled = enabled,
            isError = !errorText.isNullOrEmpty(),
            onValueChange = onValueChange,
            singleLine = singleLine,
            label = { Text(label) },
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
        AnimatedVisibility(visible = !errorText.isNullOrEmpty()) {
            Text(
                modifier = Modifier.padding(8.dp),
                text = errorText!!,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}