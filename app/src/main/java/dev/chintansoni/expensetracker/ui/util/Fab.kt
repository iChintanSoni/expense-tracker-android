package dev.chintansoni.expensetracker.ui.util

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.chintansoni.expensetracker.ui.theme.AddIcon

@Preview(showBackground = true)
@Composable
fun Fab(icon: @Composable () -> Unit = AddIcon, onClick: () -> Unit = {}) {
    val fabShape = RoundedCornerShape(50)
    FloatingActionButton(
        onClick = onClick,
        shape = fabShape,
        content = icon
    )
}