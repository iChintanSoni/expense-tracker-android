package dev.chintansoni.expensetracker.ui.category.manage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.usecase.CategoryUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ManageCategoryViewModel(
    private val categoryId: Int,
    private val categoryRepository: CategoryRepository,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        errorState.value = throwable
    }

    var category = mutableStateOf(Category.newInstance())
        private set

    var nameError = mutableStateOf<String?>(null)
        private set

    var errorState = mutableStateOf<Throwable?>(null)
        private set

    init {
        if (categoryId != 0) {
            viewModelScope.launch(exceptionHandler) {
                categoryRepository.getCategoryByIdFlow(categoryId)
                    .collectLatest { category ->
                        category?.let {
                            this@ManageCategoryViewModel.category.value = it
                        } ?: throw CategoryNotFoundException(categoryId)
                    }
            }
        }
    }

    fun setName(name: String) {
        category.value = category.value.copy(name = name)
    }

    fun setDescription(description: String) {
        category.value = category.value.copy(description = description)
    }

    fun saveCategory(category: Category, onSave: () -> Unit) {
        viewModelScope.launch {
            categoryRepository.upsertCategory(category)
            onSave()
        }
    }

    fun deleteCategory(category: Category, onDelete: () -> Unit) {
        viewModelScope.launch {
            categoryUseCase.deleteCategory(category)
            onDelete()
        }
    }
}

data class CategoryNotFoundException(val categoryId: Int) :
    Exception("No category found with id:$categoryId ")