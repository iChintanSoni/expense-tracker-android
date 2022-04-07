package dev.chintansoni.expensetracker.ui.category.list

import dev.chintansoni.domain.model.Category
import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class ListViewContract {

    sealed class Event : UiEvent {
        object FetchCategories : Event()
        data class NavigateToDetail(val id: Long) : Event()
        object NavigateBack : Event()
    }

    data class State(val categoryList: List<Category>) : UiState {
        companion object {
            fun default() = State(categoryList = emptyList())
        }
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
        data class NavigateToDetail(val id: Long) : Effect()
        object NavigateBack : Effect()
    }
}