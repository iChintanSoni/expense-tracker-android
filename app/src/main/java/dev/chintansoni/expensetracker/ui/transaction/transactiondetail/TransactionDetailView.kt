package dev.chintansoni.expensetracker.ui.transaction.transactiondetail

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
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
import dev.chintansoni.expensetracker.ui.theme.DoneIcon
import dev.chintansoni.expensetracker.ui.theme.DrawableIcon
import dev.chintansoni.expensetracker.ui.theme.DropDownIcon
import dev.chintansoni.expensetracker.ui.theme.EventIcon
import dev.chintansoni.expensetracker.ui.theme.NoteIcon
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.MainToolbar
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
        parametersOf(transactionId)
    }

    val transaction: Transaction by transactionDetailViewModel.transactionStateFlow.collectAsState()

    val amountError by transactionDetailViewModel.amountErrorStateFlow.collectAsState()
    val onAmountErrorChange: (String) -> Unit = {
        transactionDetailViewModel.setAmountError(it)
    }

    val categories by transactionDetailViewModel.categoriesStateFlow.collectAsState()

    val onAmountChange: (String) -> Unit = {
        transactionDetailViewModel.setAmount(it)
    }
    val onDateChange: (DateTime) -> Unit = {
        transactionDetailViewModel.setDate(it)
    }

    val onNoteChange: (String) -> Unit = {
        transactionDetailViewModel.setNote(it)
    }

    val categoryChange: (Int) -> Unit = {
        transactionDetailViewModel.setCategory(it)
    }

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
        onAmountChange = onAmountChange,
        amountError = amountError,
        onAmountErrorChange = onAmountErrorChange,
        onDateChange = onDateChange,
        onNoteChange = onNoteChange,
        categories = categories,
        onCategorySelected = categoryChange,
        onDoneClick = onDoneClick
    )
}

@Preview(showBackground = true)
@Composable
fun AddEditExpenseContent(
    onBackClick: () -> Unit = {},
    transaction: Transaction = Transaction.newInstance(),
    onAmountChange: (String) -> Unit = {},
    amountError: String? = null,
    onAmountErrorChange: (String) -> Unit = {},
    onDateChange: (DateTime) -> Unit = {},
    onNoteChange: (String) -> Unit = {},
    categories: List<Category> = emptyList(),
    onCategorySelected: (Int) -> Unit = {},
    onDoneClick: (Transaction) -> Unit = {}
) {

    var localAmountState by remember {
        mutableStateOf(transaction.printableAmount())
    }

    Scaffold(
        topBar = {
            MainToolbar(
                title = "Expense Details",
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            Fab(DoneIcon) {
                onDoneClick(transaction)
            }
        }
    ) {
        Column(Modifier.padding(16.dp)) {

            TextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                label = "Amount",
                leadingIcon = { DrawableIcon(resId = R.drawable.currency_inr) },
                value = localAmountState,
                errorText = amountError,
                onValueChange = {
                    localAmountState = it
                    if (it.toFloatOrNull() != null) {
                        onAmountChange(it)
                        onAmountErrorChange("")
                    } else {
                        onAmountErrorChange("Please enter a valid amount")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                CategoryView(transaction.category, onCategorySelected, categories)

                Spacer(modifier = Modifier.width(16.dp))

                DatePicker(
                    selectedDateTime = transaction.date.toDateTime(),
                    onDateSelected = onDateChange
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = transaction.note ?: "",
                leadingIcon = NoteIcon,
                maxLines = 5,
                onValueChange = onNoteChange,
                label = { Text("Notes") },
            )
        }
    }
}

@Composable
fun RowScope.DatePicker(
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
