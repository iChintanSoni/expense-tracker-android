@file:OptIn(ExperimentalFoundationApi::class)

package dev.chintansoni.expensetracker.ui.category.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.domain.model.Category
import dev.chintansoni.expensetracker.ui.navigator.MainViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.navigator.navigateBack
import dev.chintansoni.expensetracker.ui.theme.AddIcon
import dev.chintansoni.expensetracker.ui.theme.NavigateNextIcon
import dev.chintansoni.expensetracker.ui.util.Fab
import dev.chintansoni.expensetracker.ui.util.MainToolbar
import org.koin.androidx.compose.viewModel

const val ROUTE_CATEGORIES = "categories"

fun NavGraphBuilder.categoriesRoute(navController: NavController) {
    composable(ROUTE_CATEGORIES) {
        CategoriesView(navController)
    }
}

@Composable
fun CategoriesView(navController: NavController = rememberNavController()) {

    val viewModel: ListViewModel by viewModel()
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = ListViewContract.Effect.Nothing)

    LaunchedEffect(key1 = effect, block = {
        when (effect) {
            is ListViewContract.Effect.NavigateToDetail -> {
                navController.navigate(MainViewRoute.CategoryDetailViewRoute((effect as ListViewContract.Effect.NavigateToDetail).id))
            }
            is ListViewContract.Effect.NavigateBack -> {
                navController.navigateBack()
            }
            else -> {}
        }
    })

    val onBackClick: () -> Unit = {
        viewModel.setEvent(ListViewContract.Event.NavigateBack)
    }
    BackHandler { onBackClick() }

    val onCategoryClick: (Category) -> Unit = {
        viewModel.setEvent(ListViewContract.Event.NavigateToDetail(it.id))
    }
    val onAddClick: () -> Unit = {
        navController.navigate(MainViewRoute.CategoryDetailViewRoute(0))
    }

    CategoriesContent(
        categories = state.categoryList,
        onBackClick = onBackClick,
        onAddClick = onAddClick,
        onCategoryClick = onCategoryClick
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun CategoriesContent(
    categories: List<Category> = listOf(Category.dummyInstance(), Category.dummyInstance()),
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onCategoryClick: (Category) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainToolbar("Categories", onBackClick) },
        floatingActionButton = {
            Fab(
                icon = AddIcon,
                onClick = onAddClick
            )
        },
    ) {
        Box(modifier = Modifier.padding(it)) {
            LazyColumn {
                itemsIndexed(categories) { index, category ->
                    CategoryItem(
                        modifier = Modifier.animateItemPlacement(),
                        category = category,
                        onCategoryClick = onCategoryClick
                    )
                    if (index < categories.lastIndex)
                        Divider(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category = Category.dummyInstance(),
    onCategoryClick: (Category) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onCategoryClick(category) }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.subtitle1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = category.description,
                style = MaterialTheme.typography.caption,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        NavigateNextIcon()
    }
}

