package dev.chintansoni.expensetracker.ui.category.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import dev.chintansoni.expensetracker.ui.navigator.BackViewRoute
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
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

    val onBackClick: () -> Unit = {
        navController.navigate(BackViewRoute)
    }
    BackHandler { onBackClick() }

    val manageCategoriesViewModel: CategoriesViewModel by viewModel()
    val categories by remember { manageCategoriesViewModel.categories }
    val onCategoryClick: (Category) -> Unit = {
        navController.navigate(MainRoute.ManageCategoryViewRoute(it.id))
    }
    val onAddClick: () -> Unit = {
        navController.navigate(MainRoute.ManageCategoryViewRoute(0))
    }

    CategoriesContent(
        categories,
        onBackClick = onBackClick,
        onAddClick = onAddClick,
        onCategoryClick = onCategoryClick
    )
}

@Preview(showBackground = true)
@Composable
fun CategoriesContent(
    categories: List<Category> = listOf(Category.dummyInstance()),
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onCategoryClick: (Category) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { MainToolbar("Categories", onBackClick) },
        floatingActionButton = {
            Fab(AddIcon) { onAddClick() }
        }
    ) {
        LazyColumn {
            items(categories) { category ->
                CategoryItem(category, onCategoryClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItem(
    category: Category = Category.dummyInstance(),
    onCategoryClick: (Category) -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { onCategoryClick(category) }
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
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
