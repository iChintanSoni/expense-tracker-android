package dev.chintansoni.expensetracker.ui.transaction.transactiondetail

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.chintansoni.common.DateTime
import dev.chintansoni.common.currentDateTime
import dev.chintansoni.common.dateToDateTime
import dev.chintansoni.common.getDay
import dev.chintansoni.common.getMonth
import dev.chintansoni.common.getYear
import dev.chintansoni.common.toDateTime
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.category.dropdown.CategoryView
import dev.chintansoni.expensetracker.ui.navigator.BackViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.BackIcon
import dev.chintansoni.expensetracker.ui.theme.DeleteIcon
import dev.chintansoni.expensetracker.ui.theme.DoneIcon
import dev.chintansoni.expensetracker.ui.theme.DrawableIcon
import dev.chintansoni.expensetracker.ui.theme.DropDownIcon
import dev.chintansoni.expensetracker.ui.theme.EditIcon
import dev.chintansoni.expensetracker.ui.theme.EventIcon
import dev.chintansoni.expensetracker.ui.theme.NoteIcon
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

const val PARAM_TRANSACTION_DETAIL = "transactionId"
const val ROUTE_TRANSACTION_DETAIL = "transactionDetail/{$PARAM_TRANSACTION_DETAIL}"

fun transactionDetailRoute(transactionId: Long): String {
    return "transactionDetail/$transactionId"
}

val arguments = listOf(navArgument(PARAM_TRANSACTION_DETAIL) {
    type = NavType.LongType
    defaultValue = 0L
})

fun NavBackStackEntry.argumentTransactionId(): Long {
    return arguments?.getLong(PARAM_TRANSACTION_DETAIL, 0L) ?: 0L
}

fun NavGraphBuilder.transactionDetailRoute(navController: NavController) {
    composable(ROUTE_TRANSACTION_DETAIL, arguments) {
        TransactionDetailView(transactionId = it.argumentTransactionId(), navController)
    }
}

@Composable
fun TransactionDetailView(
    transactionId: Long,
    navController: NavController = rememberNavController()
) {

    val transactionDetailViewModel: TransactionDetailViewModel by viewModel {
        parametersOf(
            transactionId
        )
    }

    val isEditMode: Boolean by transactionDetailViewModel.isEditModeStateFlow.collectAsState()
    val transaction: Transaction by transactionDetailViewModel.transactionStateFlow.collectAsState()
    val amount by transactionDetailViewModel.amountStateFlow.collectAsState()
    val amountError by transactionDetailViewModel.amountErrorStateFlow.collectAsState()
    val categories by transactionDetailViewModel.categoriesStateFlow.collectAsState()

    val onBackClick: () -> Unit = {
        navController.navigate(BackViewRoute)
    }

    val onDoneClick: (Transaction) -> Unit = {
        transactionDetailViewModel.addUpdateExpense(it)
    }

    BackHandler { onBackClick() }

    AddEditExpenseContent(
        onBackClick = onBackClick,
        transaction = transaction,
        amount = amount,
        onAmountChange = transactionDetailViewModel::setAmount,
        amountError = amountError,
        onAmountErrorChange = transactionDetailViewModel::setAmountError,
        onDateChange = transactionDetailViewModel::setDate,
        onNoteChange = transactionDetailViewModel::setNote,
        categories = categories,
        onCategorySelected = transactionDetailViewModel::setCategory,
        onDoneClick = onDoneClick,
        isEditMode = isEditMode,
        toggleEditMode = transactionDetailViewModel::toggleEditMode
    )
}

@Preview(showBackground = true)
@Composable
fun AddEditExpenseContent(
    onBackClick: () -> Unit = {},
    transaction: Transaction = Transaction.newInstance(),
    amount: String = "",
    onAmountChange: (String) -> Unit = {},
    amountError: String? = null,
    onAmountErrorChange: (String) -> Unit = {},
    onDateChange: (DateTime) -> Unit = {},
    onNoteChange: (String) -> Unit = {},
    categories: List<Category> = emptyList(),
    onCategorySelected: (Int) -> Unit = {},
    onDeleteTransaction: (Transaction) -> Unit = {},
    onDoneClick: (Transaction) -> Unit = {},
    isEditMode: Boolean = false,
    toggleEditMode: () -> Unit = {},
) {
    var shouldShowConfirmDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        content = BackIcon
                    )
                },
                actions = {
                    if (!isEditMode) {
                        IconButton(onClick = { shouldShowConfirmDialog = true }) {
                            DeleteIcon()
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (isEditMode) {
                Fab(DoneIcon) {
                    onDoneClick(transaction)
                    toggleEditMode()
                }
            } else {
                Fab(EditIcon) {
                    toggleEditMode()
                }
            }
        }
    ) {
        Column(Modifier.padding(16.dp)) {
            TextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                label = "Amount",
                leadingIcon = { DrawableIcon(resId = R.drawable.currency_inr) },
                value = amount,
                enabled = isEditMode,
                errorText = amountError,
                onValueChange = onAmountChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                CategoryView(
                    selectedCategory = transaction.category,
                    onCategorySelected = onCategorySelected,
                    enabled = isEditMode,
                    categories = categories
                )

                Spacer(modifier = Modifier.width(16.dp))

                DatePicker(
                    enabled = isEditMode,
                    selectedDateTime = transaction.date.toDateTime(),
                    onDateSelected = onDateChange
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = transaction.note ?: "",
                enabled = isEditMode,
                leadingIcon = NoteIcon,
                maxLines = 5,
                onValueChange = onNoteChange,
                label = { Text("Notes") },
            )
        }
    }

    if (shouldShowConfirmDialog) {
        AlertDialog(
            onDismissRequest = {
                shouldShowConfirmDialog = false
            },
            title = {
                Text(text = "Confirm")
            },
            text = {
                Text(
                    "Are you sure you want to delete this transaction?\nNote: This action cannot be reverted."
                )
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = { shouldShowConfirmDialog = false }
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            shouldShowConfirmDialog = false
                            onDeleteTransaction(transaction)
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        )
    }
}

@Composable
fun RowScope.DatePicker(
    enabled: Boolean = true,
    selectedDateTime: DateTime = currentDateTime(),
    onDateSelected: (DateTime) -> Unit = {}
) {

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onDateSelected(dateToDateTime(day = dayOfMonth, month = month + 1, year = year))
        },
        selectedDateTime.getYear(),
        selectedDateTime.getMonth() - 1,
        selectedDateTime.getDay()
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        value = selectedDateTime.toDateTime().date.toString(),
        enabled = enabled,
        leadingIcon = EventIcon,
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = { datePickerDialog.show() },
                content = DropDownIcon
            )
        },
        onValueChange = {},
        readOnly = true,
        label = { Text("Date") },
    )
}
