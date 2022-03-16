package dev.chintansoni.expensetracker.ui.transaction.detail

import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.model.TransactionDetail
import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class DetailViewContract {

    sealed class Event : UiEvent {
        data class OnAmountChange(val amount: String) : Event()
        data class OnCategoryChange(val category: Long) : Event()
        data class OnDateChange(val date: Long) : Event()
        data class OnNoteChange(val note: String) : Event()
        data class FetchDetails(val transactionId: Long) : Event()
        object ToggleEditMode : Event()
        object ToggleDeleteMode : Event()
        object OnConfirmDelete : Event()
        object OnDoneClick : Event()
        object OnBackClick : Event()
    }

    data class State(
        val transactionDetail: TransactionDetail,
        val categories: List<Category>,
        val amountAsString: String,
        val amountError: String,
        val isEditMode: Boolean,
        val isDeleteMode: Boolean
    ) : UiState {
        companion object {
            fun default() = State(
                transactionDetail = TransactionDetail.defaultInstance(),
                categories = emptyList(),
                amountAsString = "",
                amountError = "",
                isEditMode = false,
                isDeleteMode = false
            )
        }
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
        object NavigateBack : Effect()
    }
}