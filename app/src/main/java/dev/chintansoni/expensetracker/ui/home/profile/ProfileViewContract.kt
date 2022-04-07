package dev.chintansoni.expensetracker.ui.home.profile

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class ProfileViewContract {

    sealed class Event : UiEvent {
        object OnLogoutClick : Event()
    }

    data class State(val name: String = "Chintan Soni") : UiState {
        companion object {
            fun default() = State()
        }
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
        object NavigateToSignIn : Effect()
    }
}