package dev.chintansoni.expensetracker.ui.category.dropdown

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chintansoni.domain.model.Category
import dev.chintansoni.expensetracker.ui.theme.CategoryIcon
import org.koin.androidx.compose.inject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowScope.CategoryView(
    selectedCategory: Int = 0,
    onCategorySelected: (Int) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val onExpandChange: (Boolean) -> Unit = {
        expanded = it
    }
    val categoryViewModel: CategoryViewModel by inject()
    val categories by remember {
        categoryViewModel.categories
    }
    CategoryContent(
        expand = expanded,
        onExpandChange = onExpandChange,
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected,
        categories = categories
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun RowScope.CategoryContent(
    expand: Boolean = false,
    onExpandChange: (Boolean) -> Unit = {},
    selectedCategory: Int = 0,
    onCategorySelected: (Int) -> Unit = {},
    categories: List<Category> = emptyList()
) {
    ExposedDropdownMenuBox(
        expanded = expand,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        onExpandedChange = onExpandChange
    ) {
        OutlinedTextField(
            readOnly = true,
            value = if (categories.isNotEmpty()) categories[selectedCategory].name else "",
            modifier = Modifier
                .fillMaxWidth(),
            onValueChange = { },
            label = { Text("Category") },
            leadingIcon = CategoryIcon,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expand
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expand,
            onDismissRequest = {
                onExpandChange(false)
            }
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        onCategorySelected(category.id)
                        onExpandChange(false)
                    }
                ) {
                    Text(
                        text = category.name
                    )
                }
            }
        }
    }
}