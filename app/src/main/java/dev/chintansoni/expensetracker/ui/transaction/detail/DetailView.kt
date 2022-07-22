package dev.chintansoni.expensetracker.ui.transaction.detail

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import dev.chintansoni.common.currentDateTimeInMillis
import dev.chintansoni.common.dateToDateTime
import dev.chintansoni.common.getDay
import dev.chintansoni.common.getMonth
import dev.chintansoni.common.getYear
import dev.chintansoni.common.toDateTime
import dev.chintansoni.common.toPrintableDate
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
import dev.chintansoni.expensetracker.ui.util.Action
import dev.chintansoni.expensetracker.ui.util.Alert
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

const val PARAM_TRANSACTION_DETAIL = "transactionId"
const val ROUTE_TRANSACTION_DETAIL = "transactionDetail/{$PARAM_TRANSACTION_DETAIL}"

fun transactionDetailRoute(transactionId: Long): String {
    return "transactionDetail/$transactionId"
}

val arguments = listOf(navArgument(name = PARAM_TRANSACTION_DETAIL) {
    type = NavType.LongType
    defaultValue = 0L
})

fun NavBackStackEntry.argumentTransactionId(): Long {
    return arguments?.getLong(PARAM_TRANSACTION_DETAIL, 0L) ?: 0L
}

fun NavGraphBuilder.transactionDetailRoute(navController: NavController) {
    composable(ROUTE_TRANSACTION_DETAIL, arguments) {
        DetailView(
            transactionId = it.argumentTransactionId(),
            navController = navController
        )
    }
}

@Composable
fun DetailView(
    transactionId: Long,
    navController: NavController = rememberNavController()
) {

    val viewModel: DetailViewModel by viewModel {
        parametersOf(
            transactionId
        )
    }

    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = DetailViewContract.Effect.Nothing)

    LaunchedEffect(key1 = effect, block = {
        when (effect) {
            DetailViewContract.Effect.Nothing -> {}
            DetailViewContract.Effect.NavigateBack -> navController.navigate(BackViewRoute)
        }
    })

    val onBackClick: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnBackClick)
    }

    val onDoneClick: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnDoneClick)
    }

    val onConfirmDelete: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnConfirmDelete)
    }

    val onAmountChange: (String) -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnAmountChange(it))
    }

    val onDateChange: (Long) -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnDateChange(it))
    }

    val onNoteChange: (String) -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnNoteChange(it))
    }

    val onCategoryChange: (Long) -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnCategoryChange(it))
    }

    val toggleEditMode: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.ToggleEditMode)
    }

    val toggleDeleteMode: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.ToggleDeleteMode)
    }

    BackHandler { onBackClick() }

    AddEditExpenseContent(
        onBackClick = onBackClick,
        state = state,
        onAmountChange = onAmountChange,
        onDateChange = onDateChange,
        onNoteChange = onNoteChange,
        onCategorySelected = onCategoryChange,
        onDoneClick = onDoneClick,
        onConfirmDelete = onConfirmDelete,
        toggleEditMode = toggleEditMode,
        toggleDeleteMode = toggleDeleteMode,
    )
}

@Preview(showBackground = true)
@Composable
fun AddEditExpenseContent(
    onBackClick: () -> Unit = {},
    state: DetailViewContract.State = DetailViewContract.State.default(),
    onAmountChange: (String) -> Unit = {},
    onAmountErrorChange: (String) -> Unit = {},
    onDateChange: (Long) -> Unit = {},
    onNoteChange: (String) -> Unit = {},
    onCategorySelected: (Long) -> Unit = {},
    onDoneClick: () -> Unit = {},
    toggleEditMode: () -> Unit = {},
    toggleDeleteMode: () -> Unit = {},
    onConfirmDelete: () -> Unit = {}
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
                    if (!state.isEditMode) {
                        IconButton(onClick = { shouldShowConfirmDialog = true }) {
                            DeleteIcon()
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (state.isEditMode) {
                Fab(DoneIcon) {
                    onDoneClick()
                    toggleEditMode()
                }
            } else {
                Fab(EditIcon) {
                    toggleEditMode()
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(Modifier.padding(16.dp)) {
                TextFieldWithError(
                    modifier = Modifier.fillMaxWidth(),
                    label = "Amount",
                    leadingIcon = { DrawableIcon(resId = R.drawable.currency_inr) },
                    value = state.amountAsString,
                    enabled = state.isEditMode,
                    errorText = state.amountError,
                    onValueChange = onAmountChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {

                    CategoryView(
                        enabled = state.isEditMode,
                        selectedCategory = state.transactionDetail.categoryId,
                        onCategorySelected = onCategorySelected,
                        categories = state.categories
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    DatePicker(
                        enabled = state.isEditMode,
                        selectedDateTime = state.transactionDetail.date,
                        onDateSelected = onDateChange
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.transactionDetail.note ?: "",
                    enabled = state.isEditMode,
                    leadingIcon = NoteIcon,
                    maxLines = 5,
                    onValueChange = onNoteChange,
                    label = { Text("Notes") },
                )
            }
            if (shouldShowConfirmDialog) {
                Alert(
                    title = "Confirm",
                    message = "Are you sure you want to delete this transaction?\nNote: This action cannot be reverted.",
                    actions = listOf(
                        Action(
                            label = "Cancel",
                            onClick = { shouldShowConfirmDialog = false }
                        ),
                        Action(
                            label = "Delete",
                            onClick = {
                                shouldShowConfirmDialog = false
                                onConfirmDelete()
                            }
                        )
                    ),
                    onDismissRequest = { shouldShowConfirmDialog = false }
                )
            }
        }
    }
}

@Composable
fun RowScope.DatePicker(
    enabled: Boolean = true,
    selectedDateTime: Long = currentDateTimeInMillis(),
    onDateSelected: (Long) -> Unit = {}
) {

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            onDateSelected(
                dateToDateTime(
                    day = dayOfMonth,
                    month = month + 1,
                    year = year
                ).toEpochMilliseconds()
            )
        },
        selectedDateTime.toDateTime().getYear(),
        selectedDateTime.toDateTime().getMonth() - 1,
        selectedDateTime.toDateTime().getDay()
    )

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        value = selectedDateTime.toPrintableDate(),
        enabled = enabled,
        leadingIcon = EventIcon,
        singleLine = true,
        trailingIcon = {
            IconButton(
                onClick = {
                    if (enabled) {
                        datePickerDialog.show()
                    }
                },
                content = DropDownIcon
            )
        },
        onValueChange = {},
        readOnly = true,
        label = { Text("Date") },
    )
}
