package dev.chintansoni.expensetracker.ui.category.manage

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dev.chintansoni.domain.model.Category
import dev.chintansoni.expensetracker.ui.navigator.BackViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.BackIcon
import dev.chintansoni.expensetracker.ui.theme.DeleteIcon
import dev.chintansoni.expensetracker.ui.theme.DoneIcon
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

const val PARAM_CATEGORY_ID = "categoryId"
const val ROUTE_MANAGE_CATEGORY = "manage-category/{${PARAM_CATEGORY_ID}}"

fun buildManageCategoryRoute(categoryId: Int): String {
    return "manage-category/$categoryId"
}

private val arguments = listOf(navArgument(PARAM_CATEGORY_ID) { type = NavType.IntType })

fun NavBackStackEntry.argumentCategoryId(): Int {
    return arguments?.getInt(PARAM_CATEGORY_ID, 0) ?: 0
}

fun NavGraphBuilder.manageCategoryRoute(navController: NavController) {
    composable(ROUTE_MANAGE_CATEGORY, arguments) {
        ManageCategoryView(navController, categoryId = it.argumentCategoryId())
    }
}

@Composable
fun ManageCategoryView(
    navController: NavController = rememberNavController(),
    categoryId: Int = 0
) {
    val onBackClick: () -> Unit = {
        navController.navigate(BackViewRoute)
    }
    BackHandler { onBackClick() }

    val manageCategoryViewModel: ManageCategoryViewModel by viewModel {
        parametersOf(categoryId)
    }
    val category by remember { manageCategoryViewModel.category }
    val nameError by remember { manageCategoryViewModel.nameError }
    val errorState by remember { manageCategoryViewModel.errorState }

    ManageCategoryContent(
        errorState = errorState,
        category = category,
        nameError = nameError,
        onNameChange = manageCategoryViewModel::setName,
        onDescriptionChange = manageCategoryViewModel::setDescription,
        onBackClick = onBackClick,
        onDeleteCategory = {
            manageCategoryViewModel.deleteCategory(it) {
                onBackClick()
            }
        },
        onDoneClick = {
            manageCategoryViewModel.saveCategory(it) {
                onBackClick()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun ManageCategoryContent(
    errorState: Throwable? = null,
    category: Category = Category.dummyInstance(),
    nameError: String? = null,
    onNameChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onDeleteCategory: (Category) -> Unit = {},
    onDoneClick: (Category) -> Unit = {},
) {
    val scaffoldState = rememberScaffoldState()
    var shouldShowConfirmDialog by remember { mutableStateOf(false) }
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        content = BackIcon
                    )
                },
                title = {
                    Text(text = category.name)
                },
                actions = {
                    if (category.canBeDeleted()) {
                        IconButton(onClick = { shouldShowConfirmDialog = true }) {
                            DeleteIcon()
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            Fab(DoneIcon) { onDoneClick(category) }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
        ) {
            TextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                value = category.name,
                label = "Name",
                onValueChange = onNameChange,
                errorText = nameError
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldWithError(
                modifier = Modifier.fillMaxWidth(),
                value = category.description,
                label = "Description",
                onValueChange = onDescriptionChange
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
                    "Are you sure you want to delete this category?\nNote: Expenses with category as ${category.name} will be changed to Uncategorized."
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
                            onDeleteCategory(category)
                        }
                    ) {
                        Text("Delete")
                    }
                }
            }
        )
    }

    if (errorState is CategoryNotFoundException) {
        LaunchedEffect(key1 = errorState) {
            scaffoldState.snackbarHostState.showSnackbar(
                errorState.message ?: "",
                duration = SnackbarDuration.Indefinite
            )
        }
    }
}