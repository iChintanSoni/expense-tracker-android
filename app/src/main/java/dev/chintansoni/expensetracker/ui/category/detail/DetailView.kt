package dev.chintansoni.expensetracker.ui.category.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import dev.chintansoni.expensetracker.ui.navigator.navigateBack
import dev.chintansoni.expensetracker.ui.theme.BackIcon
import dev.chintansoni.expensetracker.ui.theme.DeleteIcon
import dev.chintansoni.expensetracker.ui.theme.DoneIcon
import dev.chintansoni.expensetracker.ui.theme.EditIcon
import dev.chintansoni.expensetracker.ui.util.Action
import dev.chintansoni.expensetracker.ui.util.Alert
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel
import org.koin.core.parameter.parametersOf

const val PARAM_CATEGORY_ID = "categoryId"
const val ROUTE_CATEGORY_DETAIL = "category-detail/{${PARAM_CATEGORY_ID}}"

fun buildCategoryDetailRoute(categoryId: Long = 0): String {
    return "category-detail/$categoryId"
}

private val arguments = listOf(navArgument(PARAM_CATEGORY_ID) { type = NavType.LongType })

fun NavBackStackEntry.argumentCategoryId(): Long {
    return arguments?.getLong(PARAM_CATEGORY_ID, 0L) ?: 0L
}

fun NavGraphBuilder.categoryDetailRoute(navController: NavController) {
    composable(ROUTE_CATEGORY_DETAIL, arguments) {
        CategoryDetailView(navController, categoryId = it.argumentCategoryId())
    }
}

@Composable
fun CategoryDetailView(
    navController: NavController = rememberNavController(),
    categoryId: Long = 0
) {

    val scaffoldState = rememberScaffoldState()

    val viewModel: DetailViewModel by viewModel { parametersOf(categoryId) }

    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = DetailViewContract.Effect.NoEffect)

    LaunchedEffect(
        key1 = effect,
        block = {
            when (effect) {
                is DetailViewContract.Effect.CategoryNotFound -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = (effect as DetailViewContract.Effect.CategoryNotFound).message,
                        duration = SnackbarDuration.Indefinite
                    )
                }
                is DetailViewContract.Effect.DeleteFinished -> {
                    navController.navigateBack()
                }
                is DetailViewContract.Effect.NavigateBack -> {
                    navController.navigateBack()
                }
                else -> {}
            }
        }
    )

    val nameChange: (String) -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.NameChange(it))
    }

    val descriptionChange: (String) -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.DescriptionChange(it))
    }

    val toggleEditMode: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.ToggleEditMode)
    }

    val toggleDeleteMode: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.ToggleDeleteMode)
    }

    val onSaveClick: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnSave)
    }

    val onDeleteClick: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnDeleteClick)
    }

    val onDeleteConfirm: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnDeleteConfirm)
    }
    val onBackClick: () -> Unit = {
        viewModel.setEvent(DetailViewContract.Event.OnBackClick)
    }
    BackHandler { onBackClick() }

    CategoryDetailContent(
        scaffoldState = scaffoldState,
        state = state,
        onNameChange = nameChange,
        onDescriptionChange = descriptionChange,
        onBackClick = onBackClick,
        onDeleteClick = onDeleteClick,
        onDeleteConfirm = onDeleteConfirm,
        toggleEditMode = toggleEditMode,
        toggleDeleteMode = toggleDeleteMode,
        onSaveClick = onSaveClick
    )
}

@Preview(showBackground = true)
@Composable
fun CategoryDetailContent(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    state: DetailViewContract.State = DetailViewContract.State.default(),
    onNameChange: (String) -> Unit = {},
    onDescriptionChange: (String) -> Unit = {},
    onBackClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    onDeleteConfirm: () -> Unit = {},
    toggleEditMode: () -> Unit = {},
    toggleDeleteMode: () -> Unit = {},
    onSaveClick: () -> Unit = {},
) {
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
                    Text(text = state.category.name)
                },
                actions = {
                    if (state.category.canBeDeleted() && !state.isEditMode) {
                        IconButton(
                            onClick = toggleDeleteMode,
                            content = DeleteIcon
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (state.isEditMode) {
                Fab(
                    icon = DoneIcon,
                    onClick = {
                        onSaveClick()
                        toggleEditMode()
                    }
                )
            } else {
                Fab(
                    icon = EditIcon,
                    onClick = toggleEditMode
                )
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                TextFieldWithError(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.category.name,
                    label = "Name",
                    onValueChange = onNameChange,
                    errorText = state.nameError,
                    enabled = state.isEditMode
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextFieldWithError(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.category.description,
                    label = "Description",
                    onValueChange = onDescriptionChange,
                    errorText = state.descriptionError,
                    enabled = state.isEditMode
                )
            }

            if (state.isDeleteMode) {
                Alert(
                    title = "Confirm",
                    message = """Are you sure you want to delete this category?
Note: Expenses with ${state.category.name} category will be changed to Uncategorized.""",
                    actions = listOf(
                        Action(
                            label = "Cancel",
                            onClick = toggleDeleteMode
                        ),
                        Action(
                            label = "Delete",
                            onClick = {
                                onDeleteConfirm()
                                toggleDeleteMode()
                            }
                        )
                    ),
                    onDismissRequest = { toggleDeleteMode() }
                )
            }
        }
    }
}