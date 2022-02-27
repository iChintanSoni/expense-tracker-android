package dev.chintansoni.expensetracker.ui.category.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.usecase.CategoryUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoryDetailViewModel(
    private val categoryId: Int,
    private val categoryRepository: CategoryRepository,
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _errorStateFlow.update { throwable }
    }

    private val _categoryStateFlow = MutableStateFlow(Category.newInstance())
    val categoryStateFlow = _categoryStateFlow.asStateFlow()

    private val _nameErrorStateFlow = MutableStateFlow<String?>(null)
    val nameErrorStateFlow = _nameErrorStateFlow.asStateFlow()

    private val _errorStateFlow = MutableStateFlow<Throwable?>(null)
    val errorStateFlow = _errorStateFlow.asStateFlow()

    var errorState = mutableStateOf<Throwable?>(null)
        private set

    init {
        if (categoryId != 0) {
            viewModelScope.launch(exceptionHandler) {
                categoryRepository.getCategoryByIdFlow(categoryId)
                    .collectLatest { category ->
                        category?.let {
                            this@CategoryDetailViewModel._categoryStateFlow.update { it }
                        } ?: throw CategoryNotFoundException(categoryId)
                    }
            }
        }
    }

    fun setName(name: String) {
        _categoryStateFlow.update { it.copy(name = name) }
    }

    fun setDescription(description: String) {
        _categoryStateFlow.update { it.copy(description = description) }
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