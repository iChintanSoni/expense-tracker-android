package dev.chintansoni.expensetracker.ui.auth.signup

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState
import dev.chintansoni.expensetracker.ui.util.validateEmail
import dev.chintansoni.expensetracker.ui.util.validatePassword

class SignUpContract {

    sealed class Event : UiEvent {
        data class OnFirstNameChange(val firstName: String) : Event()
        data class OnLastNameChange(val lastName: String) : Event()
        data class OnEmailChange(val email: String) : Event()
        data class OnPasswordChange(val password: String) : Event()
        object OnTryAgainClick : Event()
        object OnSignUpClick : Event()
        object OnBackClick : Event()
        object OnBackToLoginClick : Event()
    }

    data class State(
        val firstName: String,
        val firstNameError: String,
        val lastName: String,
        val lastNameError: String,
        val email: String,
        val emailError: String,
        val password: String,
        val passwordError: String,
        val signUpApiState: SignUpApiState
    ) : UiState {

        fun validateFirstName(): String {
            return if (firstName.isEmpty()) "Enter your first name" else ""
        }

        fun validateLastName(): String {
            return if (lastName.isEmpty()) "Enter your last name" else ""
        }

        fun isStateFormValid(): Boolean {
            return validateFirstName().isEmpty()
                && validateLastName().isEmpty()
                && email.validateEmail().isEmpty()
                && password.validatePassword().isEmpty()
        }

        companion object {
            fun default() = State(
                firstName = "",
                firstNameError = "",
                lastName = "",
                lastNameError = "",
                email = "",
                emailError = "",
                password = "",
                passwordError = "",
                signUpApiState = SignUpApiState.Idle,
            )
        }
    }

    sealed class SignUpApiState {
        fun isNotInProgress(): Boolean {
            return this !is InProgress
        }

        object Idle : SignUpApiState()
        object InProgress : SignUpApiState()
        data class Success(val successMessage: String = "Signup was successful. Go to Login.") :
            SignUpApiState()

        data class Failure(val errorMessage: String = "Looks like email is already registered. Try again using different email.") :
            SignUpApiState()
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
        object NavigateToSignIn : Effect()
        object NavigateBack : Effect()
    }
}