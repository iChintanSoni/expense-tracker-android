package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState
import dev.chintansoni.expensetracker.ui.util.validateEmail

class ForgotPasswordContract {

    // Events
    sealed class Event : UiEvent {
        data class OnEmailChange(val email: String) : Event()
        object OnSendLinkClick : Event()
        object OnBackClick : Event()
        object OnTryAgainClick : Event()
    }

    // State
    data class State(
        val email: String,
        val emailError: String,
        val forgotPasswordApiState: ForgotPasswordApiState
    ) : UiState {

        companion object {

            fun default() = State(
                email = "",
                emailError = "",
                forgotPasswordApiState = ForgotPasswordApiState.Idle
            )
        }

        fun validateEmail(): String = email.validateEmail()

        fun isFormStateValid(): Boolean = email.validateEmail().isEmpty()
    }

    sealed class ForgotPasswordApiState {

        fun isNotInProgress(): Boolean {
            return this !is InProgress
        }

        object Idle : ForgotPasswordApiState()
        object InProgress : ForgotPasswordApiState()
        data class Success(val successMessage: String = "Password link has been sent. Check your email.") :
            ForgotPasswordApiState()

        data class Failure(val errorMessage: String = "No user found with this email. Make sure you've entered correct email.") :
            ForgotPasswordApiState()
    }

    // Side effects
    sealed class Effect : UiEffect {
        object Idle : Effect()
        object NavigateBack : Effect()
    }
}