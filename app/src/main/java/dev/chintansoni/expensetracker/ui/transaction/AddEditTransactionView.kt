package dev.chintansoni.expensetracker.ui.transaction

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.BackIcon
import dev.chintansoni.expensetracker.ui.theme.CategoryIcon
import dev.chintansoni.expensetracker.ui.theme.DropDownIcon
import dev.chintansoni.expensetracker.ui.theme.EventIcon
import dev.chintansoni.expensetracker.ui.theme.NoteIcon
import dev.chintansoni.expensetracker.ui.util.DrawableIcon
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import dev.chintansoni.expensetracker.ui.util.currentInstant
import dev.chintansoni.expensetracker.ui.util.getDay
import dev.chintansoni.expensetracker.ui.util.getMonth
import dev.chintansoni.expensetracker.ui.util.getYear
import dev.chintansoni.expensetracker.ui.util.toDateTime
import dev.chintansoni.expensetracker.ui.util.toInstant
import kotlinx.datetime.Instant
import org.koin.androidx.compose.inject

const val PARAM_TRANSACTION_DETAIL = "transactionId"
const val ROUTE_TRANSACTION_DETAIL = "transactionDetail/{${PARAM_TRANSACTION_DETAIL}}"

val arguments = listOf(navArgument(PARAM_TRANSACTION_DETAIL) { type = NavType.IntType })

fun NavBackStackEntry.argumentTransactionId(): Int {
    return arguments?.getInt(PARAM_TRANSACTION_DETAIL, 0) ?: 0
}

fun NavGraphBuilder.transactionDetailRoute() {
    composable(ROUTE_TRANSACTION_DETAIL, arguments) {
        TransactionDetailView(transactionId = it.argumentTransactionId())
    }
}

@Composable
fun TransactionDetailView(transactionId: Int) {

    val mainNavigator: MainNavigator by inject()
    val transactionDetailViewModel: TransactionDetailViewModel by inject()
    val transaction: Transaction? by transactionDetailViewModel.getTransactionFlow(transactionId)
        .collectAsState(initial = null)

    var amount by remember { mutableStateOf("") }
    var amountError by remember { mutableStateOf("") }
    val onAmountChange: (String) -> Unit = {
        amount = it
        amountError = try {
            it.toDouble()
            ""
        } catch (exception: Exception) {
            "Please enter a valid amount"
        }
    }

    var date: Instant by remember { mutableStateOf(currentInstant()) }
    val onDateChange: (Instant) -> Unit = { date = it }

    var note: String by remember { mutableStateOf("") }
    val onNoteChange: (String) -> Unit = { note = it }

    var category: String by remember { mutableStateOf("") }
    val categoryChange: (String) -> Unit = {

    }

    BackHandler {
        mainNavigator.navigate(MainRoute.GoBackViewRoute)
    }

    AddEditExpenseContent(
        amount = amount,
        onAmountChange = onAmountChange,
        amountError = amountError,
        date = date,
        onDateChange = onDateChange,
        note = note,
        onNoteChange = onNoteChange
    )
}

@Preview(showBackground = true)
@Composable
fun AddEditExpenseContent(
    amount: String = "",
    onAmountChange: (String) -> Unit = {},
    amountError: String = "",
    date: Instant = currentInstant(),
    onDateChange: (Instant) -> Unit = {},
    note: String = "",
    onNoteChange: (String) -> Unit = {},
    category: String = "",
    onCategoryChange: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            Toolbar()
        }
    ) {
        Column(Modifier.padding(16.dp)) {

            TextFieldWithError(
                label = "Amount",
                leadingIcon = {
                    DrawableIcon(resId = R.drawable.currency_inr)
                }, value = amount, errorText = amountError, onValueChange = onAmountChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                DropdownMenu()

                Spacer(modifier = Modifier.width(16.dp))

                DatePicker(date = date, onDateChange = onDateChange)
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = note,
                leadingIcon = {
                    Icon(NoteIcon, contentDescription = "Note Icon")
                },
                maxLines = 5,
                onValueChange = onNoteChange,
                label = { Text("Notes") },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Toolbar(mainNavigator: MainNavigator = MainNavigator()) {
    TopAppBar(
        navigationIcon = {
            IconButton(
                onClick = { mainNavigator.navigate(MainRoute.GoBackViewRoute) },
            ) {
                Icon(BackIcon, "Go Back Icon")
            }
        },
        title = {
            Text(text = "Add Expense")
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RowScope.DatePicker(date: Instant = currentInstant(), onDateChange: (Instant) -> Unit = {}) {

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onDateChange(toInstant(day = dayOfMonth, month = month + 1, year = year))
        },
        date.getYear(),
        date.getMonth() - 1,
        date.getDay()
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        value = date.toDateTime().date.toString(),
        leadingIcon = {
            Icon(EventIcon, contentDescription = "Localized description")
        },
        trailingIcon = {
            IconButton(onClick = {
                datePickerDialog.show()
            }) {
                Icon(
                    DropDownIcon,
                    contentDescription = "Localized description"
                )
            }
        },
        onValueChange = {},
        readOnly = true,
        label = { Text("Date") },
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun RowScope.DropdownMenu() {
    val options = listOf(
        "Others",
        "Food",
        "Shopping",
        "Entertainment",
        "Travel",
        "Home Rent",
        "Pet Groom",
        "Recharge"
    )
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOptionText,
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = { },
            label = { Text("Category") },
            leadingIcon = {
                Icon(imageVector = CategoryIcon, contentDescription = "Category Icon")
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                    }
                ) {
                    Text(
                        text = selectionOption
                    )
                }
            }
        }
    }
}