package dev.chintansoni.expensetracker.ui.category.detail

import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.usecase.CategoryUseCase
import dev.chintansoni.domain.usecase.CategoryWithNameNotFoundException
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    categoryId: Long,
    private val categoryRepository: CategoryRepository,
    private val categoryUseCase: CategoryUseCase
) : BaseViewModel<DetailViewContract.Event, DetailViewContract.State, DetailViewContract.Effect>() {

    init {
        if (categoryId > 0) {
            setEvent(DetailViewContract.Event.FetchDetails(categoryId))
        } else {
            setEvent(DetailViewContract.Event.ToggleEditMode)
        }
    }

    private fun fetchDetails(categoryId: Long) {
        launchInIO {
            val category = categoryRepository.getCategoryById(categoryId)
                ?: throw CategoryWithIdNotFoundException(categoryId)
            setState {
                copy(category = category)
            }
        }
    }

    private fun saveCategory() {
        launchInIO {
            val categoryId = categoryRepository.upsertCategory(currentState.category)
            val updatedCategory = categoryRepository.getCategoryById(categoryId)
                ?: throw Exception("Error updating category: ${currentState.category} & getting category with Id: $categoryId")
            setState { copy(category = updatedCategory, isEditMode = false, isDeleteMode = false) }
            setEffect { DetailViewContract.Effect.SaveFinished }
        }
    }

    private fun deleteCategory() {
        launchInIO {
            categoryUseCase.deleteCategory(currentState.category)
            setEffect { DetailViewContract.Effect.DeleteFinished }
        }
    }

    override fun createInitialState(): DetailViewContract.State =
        DetailViewContract.State.default()

    override fun handleEvent(event: DetailViewContract.Event) {
        when (event) {
            is DetailViewContract.Event.FetchDetails -> {
                fetchDetails(event.categoryId)
            }
            is DetailViewContract.Event.NameChange -> {
                setState {
                    copy(
                        category = category.copy(name = event.name),
                        nameError = ""
                    )
                }
            }
            is DetailViewContract.Event.DescriptionChange -> {
                setState {
                    copy(
                        category = category.copy(description = event.description),
                        descriptionError = ""
                    )
                }
            }
            is DetailViewContract.Event.OnSave -> {
                saveCategory()
            }
            is DetailViewContract.Event.OnDeleteClick -> {
                setState { copy(isDeleteMode = true) }
            }
            is DetailViewContract.Event.OnDeleteConfirm -> {
                setState { copy(isEditMode = !isEditMode) }
                deleteCategory()
            }
            is DetailViewContract.Event.ToggleEditMode -> {
                setState { copy(isEditMode = !isEditMode) }
            }
            is DetailViewContract.Event.ToggleDeleteMode -> {
                setState { copy(isDeleteMode = !isDeleteMode) }
            }
            is DetailViewContract.Event.OnBackClick -> {
                setEffect { DetailViewContract.Effect.NavigateBack }
            }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {
        when (throwable) {
            is CategoryWithIdNotFoundException,
            is CategoryWithNameNotFoundException -> {
                setEffect { DetailViewContract.Effect.CategoryNotFound(throwable.message.orEmpty()) }
            }
        }
    }
}

data class CategoryWithIdNotFoundException(val categoryId: Long) :
    Throwable("No category found with id:$categoryId ")