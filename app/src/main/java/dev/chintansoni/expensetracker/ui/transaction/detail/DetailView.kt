package dev.chintansoni.expensetracker.ui.transaction.detail

import android.app.DatePickerDialog
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.common.*
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.category.dropdown.CategoryView
import dev.chintansoni.expensetracker.ui.navigator.BackViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.*
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
                    selectedCategory = state.transactionDetail.category,
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
                        .padding(8.dp),
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
                            onConfirmDelete()
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
    selectedDateTime: Long = currentDateTime().toEpochMilliseconds(),
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
