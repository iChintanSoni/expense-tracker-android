package dev.chintansoni.expensetracker.ui.transactiondetail

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
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.chintansoni.common.DateTime
import dev.chintansoni.common.currentDateTime
import dev.chintansoni.common.dateToDateTime
import dev.chintansoni.common.getDay
import dev.chintansoni.common.getMonth
import dev.chintansoni.common.getYear
import dev.chintansoni.common.toDateTime
import dev.chintansoni.common.toInstant
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.category.dropdown.CategoryView
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.DoneIcon
import dev.chintansoni.expensetracker.ui.theme.DrawableIcon
import dev.chintansoni.expensetracker.ui.theme.DropDownIcon
import dev.chintansoni.expensetracker.ui.theme.EventIcon
import dev.chintansoni.expensetracker.ui.theme.NoteIcon
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.MainToolbar
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import kotlinx.datetime.Instant
import org.koin.androidx.compose.inject
import org.koin.core.parameter.parametersOf

const val PARAM_TRANSACTION_DETAIL = "transactionId"
const val ROUTE_TRANSACTION_DETAIL = "transactionDetail/{${PARAM_TRANSACTION_DETAIL}}"

fun transactionDetailRoute(transactionId: Long): String {
    return "transactionDetail/$transactionId"
}

val arguments = listOf(navArgument(PARAM_TRANSACTION_DETAIL) { type = NavType.LongType })

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
    val transactionDetailViewModel: TransactionDetailViewModel by inject {
        parametersOf(transactionId)
    }

    val transaction: Transaction by transactionDetailViewModel.transaction
    val amountError by transactionDetailViewModel.amountError
    val onAmountChange: (String) -> Unit = {
        transactionDetailViewModel.setAmount(it)
    }
    val onDateChange: (DateTime) -> Unit = {
        transactionDetailViewModel.setDate(it)
    }

    val onNoteChange: (String) -> Unit = {
        transactionDetailViewModel.setNote(it)
    }

    val selectedCategory: Int by remember { mutableStateOf(transaction.category) }
    val categoryChange: (Int) -> Unit = {
        transactionDetailViewModel.setCategory(it)
    }

    BackHandler {
        mainNavigator.navigate(MainRoute.GoBackViewRoute())
    }

    AddEditExpenseContent(
        onBackClick = {
            mainNavigator.navigate(MainRoute.GoBackViewRoute())
        },
        transaction = transaction,
        onAmountChange = onAmountChange,
        amountError = amountError,
        onDateChange = onDateChange,
        onNoteChange = onNoteChange,
        selectedCategory = selectedCategory,
        onCategorySelected = categoryChange
    )
}

@Preview(showBackground = true)
@Composable
fun AddEditExpenseContent(
    onBackClick: () -> Unit = {},
    transaction: Transaction = Transaction.newInstance(),
    onAmountChange: (String) -> Unit = {},
    amountError: String? = null,
    onDateChange: (Instant) -> Unit = {},
    onNoteChange: (String) -> Unit = {},
    selectedCategory: Int = 0,
    onCategorySelected: (Int) -> Unit = {}
) {
    Scaffold(
        topBar = {
            MainToolbar(
                title = "Expense Details",
                onBackClick = onBackClick
            )
        },
        floatingActionButton = {
            Fab(DoneIcon) {

            }
        }
    ) {
        Column(Modifier.padding(16.dp)) {

            TextFieldWithError(
                label = "Amount",
                leadingIcon = {
                    DrawableIcon(resId = R.drawable.currency_inr)
                },
                value = transaction.amount.toString(),
                errorText = amountError,
                onValueChange = onAmountChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {

                CategoryView(selectedCategory, onCategorySelected)

                Spacer(modifier = Modifier.width(16.dp))

                DatePicker(date = transaction.date.toInstant(), onDateChange = onDateChange)
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

@Preview(showBackground = true)
@Composable
fun RowScope.DatePicker(date: DateTime = currentDateTime(), onDateChange: (Instant) -> Unit = {}) {

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onDateChange(dateToDateTime(day = dayOfMonth, month = month + 1, year = year))
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
        leadingIcon = EventIcon,
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
