package dev.chintansoni.expensetracker.ui.auth.splash

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class SplashContract {
    // Event
    sealed class Event : UiEvent {
        object CheckLoginStatus : Event()
    }

    // State
    data class State(val userLoggedIn: Boolean?) : UiState {
        companion object {
            fun default() = State(null)
        }
    }

    // Effect
    sealed class Effect : UiEffect {
        object Nothing : Effect()
        object NavigateToHome : Effect()
        object NavigateToSignIn : Effect()
    }
}