package dev.chintansoni.expensetracker.ui.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Action(val label: String, val onClick: () -> Unit)

@Composable
fun Alert(
    title: String? = null,
    message: String? = null,
    actions: List<Action> = emptyList(),
    onDismissRequest: (Boolean) -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest(false)
        },
        title = {
            title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.h6
                )
            }
        },
        text = {
            message?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.body1
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                actions.forEach {
                    TextButton(
                        onClick = it.onClick
                    ) {
                        Text(
                            text = it.label,
                            style = MaterialTheme.typography.button
                        )
                    }
                }
            }
        }
    )
}