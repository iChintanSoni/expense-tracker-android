package dev.chintansoni.expensetracker.ui.category.detail

import dev.chintansoni.domain.model.Category
import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class DetailViewContract {

    sealed class Event : UiEvent {
        data class NameChange(val name: String) : Event()
        data class DescriptionChange(val description: String) : Event()
        data class FetchDetails(val categoryId: Long) : Event()
        object OnSave : Event()
        object OnDeleteClick : Event()
        object OnDeleteConfirm : Event()
        object OnBackClick : Event()
        object ToggleEditMode : Event()
        object ToggleDeleteMode : Event()
    }

    data class State(
        val category: Category,
        val nameError: String,
        val descriptionError: String,
        val isDeleteMode: Boolean,
        val isEditMode: Boolean
    ) : UiState {

        companion object {

            fun default() = State(
                category = Category.newInstance(),
                nameError = "",
                descriptionError = "",
                isDeleteMode = false,
                isEditMode = false
            )
        }
    }

    sealed class Effect : UiEffect {
        data class CategoryNotFound(val message: String) : Effect()
        object SaveFinished : Effect()
        object DeleteFinished : Effect()
        object NavigateBack : Effect()
        object NoEffect : Effect()
    }
}