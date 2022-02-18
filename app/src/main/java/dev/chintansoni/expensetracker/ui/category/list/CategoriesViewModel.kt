package dev.chintansoni.expensetracker.ui.category.list

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    var categories = mutableStateOf<List<Category>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesFlow().collectLatest {
                categories.value = it
            }
        }
    }
}