package dev.chintansoni.expensetracker.ui.auth.signin

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class SignInContract {

    // Events
    sealed class Event : UiEvent {
        data class OnEmailChange(val email: String) : Event()
        data class OnPasswordChange(val password: String) : Event()
        object OnSignInClick : Event()
        object OnForgotPasswordClick : Event()
        object OnSignUpClick : Event()
    }

    // State
    data class State(
        val isLoading: Boolean,
        val email: String,
        val password: String,
        val emailError: String,
        val passwordError: String,
        val loginError: String
    ) : UiState {

        companion object {

            fun default() = State(
                isLoading = false,
                email = "",
                password = "",
                emailError = "",
                passwordError = "",
                loginError = ""
            )
        }

        fun validateEmail(): String = if (email.isEmpty())
            "Enter your email"
        else if (email.isInValidEmail())
            "Enter a valid email"
        else ""

        fun validatePassword(): String = if (password.isEmpty())
            "Enter your password"
        else ""

        fun isFormValid(): Boolean = validateEmail().isEmpty() && validatePassword().isEmpty()

        private fun String.isInValidEmail(): Boolean =
            !android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    // Side effects
    sealed class Effect : UiEffect {
        object Nothing : Effect()
        object NavigateToHome : Effect()
        object NavigateToForgotPassword : Effect()
        object NavigateToSignUp : Effect()
    }
}