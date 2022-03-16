package dev.chintansoni.expensetracker.ui.category.dropdown

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chintansoni.domain.model.Category
import dev.chintansoni.expensetracker.ui.theme.CategoryIcon

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RowScope.CategoryView(
    enabled: Boolean = false,
    selectedCategory: Long = 0,
    onCategorySelected: (Long) -> Unit = {},
    categories: List<Category>
) {
    var expanded by remember { mutableStateOf(false) }
    var isEditMode by remember { mutableStateOf(false) }
    val onExpandChange: (Boolean) -> Unit = {
        if (isEditMode) {
            expanded = it
        }
    }
    LaunchedEffect(key1 = enabled) {
        isEditMode = enabled
    }
    CategoryContent(
        expand = expanded,
        enabled = isEditMode,
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
    enabled: Boolean = false,
    onExpandChange: (Boolean) -> Unit = {},
    selectedCategory: Long = 0,
    onCategorySelected: (Long) -> Unit = {},
    categories: List<Category> = emptyList()
) {

    val fetchCategoryText: (Long, List<Category>) -> String = { selected, list ->
        if (list.isNotEmpty()) {
            if (selected == 0L) {
                list.first().name
            } else {
                list.find { it.id == selected }?.name ?: ""
            }
        } else ""
    }

    ExposedDropdownMenuBox(
        expanded = expand,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        onExpandedChange = onExpandChange
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            enabled = enabled,
            value = fetchCategoryText(selectedCategory, categories),
            singleLine = true,
            onValueChange = { },
            label = { Text("Category") },
            leadingIcon = CategoryIcon,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expand
                )
            },
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