package dev.chintansoni.expensetracker.ui.category.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categoryListStateFlow = MutableStateFlow<List<Category>>(emptyList())
    val categoryListStateFlow = _categoryListStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            categoryRepository.getAllCategoriesFlow().collectLatest {
                _categoryListStateFlow.update { it }
            }
        }
    }
}