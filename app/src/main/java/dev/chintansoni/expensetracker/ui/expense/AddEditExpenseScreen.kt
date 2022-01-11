package dev.chintansoni.expensetracker.ui.expense

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.EventNote
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.BackIcon
import dev.chintansoni.expensetracker.ui.util.DrawableIcon
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import dev.chintansoni.expensetracker.ui.util.currentInstant
import dev.chintansoni.expensetracker.ui.util.getDay
import dev.chintansoni.expensetracker.ui.util.getMonth
import dev.chintansoni.expensetracker.ui.util.getYear
import dev.chintansoni.expensetracker.ui.util.toDateTime
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import org.koin.androidx.compose.inject

const val ROUTE_ADD_EDIT_EXPENSE = "AddEditExpense"

@Composable
fun AddEditExpenseScreen() {

    val mainNavigator: MainNavigator by inject()

    var amount by remember {
        mutableStateOf("")
    }
    var amountError by remember {
        mutableStateOf("")
    }
    val onAmountChange: (String) -> Unit = {
        amount = it
        amountError = try {
            it.toDouble()
            ""
        } catch (exception: Exception) {
            "Please enter a valid amount"
        }
    }

    var date by remember {
        mutableStateOf(currentInstant())
    }
    val dateSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            date = LocalDateTime(
                year,
                monthOfYear,
                dayOfMonth,
                0,
                0,
                0
            ).toInstant(TimeZone.currentSystemDefault())
        }
    val datePickerDialog = DatePickerDialog(
        LocalContext.current, dateSetListener,
        date.getYear(),
        date.getMonth(),
        date.getDay()
    )

    var notes by remember {
        mutableStateOf("")
    }

    BackHandler {
        mainNavigator.navigate(MainRoute.GoBackViewRoute)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = { mainNavigator.navigate(MainRoute.GoBackViewRoute) },
                    ) {
                        Icon(BackIcon, "")
                    }
                },
                title = {
                    Text(text = "Add Expense")
                }
            )
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

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = notes,
                leadingIcon = {
                    Icon(Icons.Filled.EventNote, contentDescription = "Localized description")
                },
                maxLines = 5,
                onValueChange = {
                    notes = it
                },
                label = { Text("Notes") },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                DropdownMenu(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    value = date.toDateTime().date.toString(),
                    leadingIcon = {
                        Icon(Icons.Default.Event, contentDescription = "Localized description")
                    },
                    trailingIcon = {
                        IconButton(onClick = {
                            datePickerDialog.show()
                        }) {
                            Icon(
                                Icons.Filled.ArrowDropDown,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Date") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownMenu(modifier: Modifier) {
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
        modifier = modifier,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOptionText,
            modifier = modifier,
            onValueChange = { },
            label = { Text("Label") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Category, contentDescription = "Category Icon")
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