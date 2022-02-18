package dev.chintansoni.expensetracker.ui.util

import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.chintansoni.expensetracker.ui.theme.BackIcon

@Preview(showBackground = true)
@Composable
fun MainToolbar(title: String = "", onBackClick: () -> Unit = {}) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = onBackClick,
                content = BackIcon
            )
        },
        title = {
            Text(text = title)
        }
    )
}