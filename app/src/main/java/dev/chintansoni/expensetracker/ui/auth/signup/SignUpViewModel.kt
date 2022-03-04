package dev.chintansoni.expensetracker.ui.auth.signup

import androidx.lifecycle.viewModelScope
import dev.chintansoni.expensetracker.base.BaseViewModel
import dev.chintansoni.expensetracker.ui.util.validateEmail
import dev.chintansoni.expensetracker.ui.util.validatePassword
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class SignUpViewModel :
    BaseViewModel<SignUpContract.Event, SignUpContract.State, SignUpContract.Effect>() {

    override fun createInitialState(): SignUpContract.State = SignUpContract.State.default()

    override fun handleEvent(event: SignUpContract.Event) = when (event) {
        is SignUpContract.Event.OnFirstNameChange -> setState {
            copy(
                firstName = event.firstName,
                firstNameError = ""
            )
        }
        is SignUpContract.Event.OnLastNameChange -> setState {
            copy(
                lastName = event.lastName,
                lastNameError = ""
            )
        }
        is SignUpContract.Event.OnEmailChange -> setState {
            copy(
                email = event.email,
                emailError = ""
            )
        }
        is SignUpContract.Event.OnPasswordChange -> setState {
            copy(
                password = event.password,
                passwordError = ""
            )
        }
        SignUpContract.Event.OnBackClick -> setEffect { SignUpContract.Effect.NavigateBack }
        SignUpContract.Event.OnBackToLoginClick -> setEffect { SignUpContract.Effect.NavigateBack }
        SignUpContract.Event.OnTryAgainClick -> setState { copy(signUpApiState = SignUpContract.SignUpApiState.Idle) }
        SignUpContract.Event.OnSignUpClick -> performSignUp()
    }

    private fun performSignUp() {
        setState {
            copy(
                firstNameError = validateFirstName(),
                lastNameError = validateLastName(),
                emailError = email.validateEmail(),
                passwordError = password.validatePassword()
            )
        }

        if (currentState.isStateFormValid()) {
            viewModelScope.launch {
                setState { copy(signUpApiState = SignUpContract.SignUpApiState.InProgress) }
                delay(1500)
                val result = Random.nextBoolean()
                if (result) {
                    setState {
                        copy(
                            signUpApiState = SignUpContract.SignUpApiState.Success()
                        )
                    }
                } else {
                    setState {
                        copy(
                            signUpApiState = SignUpContract.SignUpApiState.Failure()
                        )
                    }
                }
            }
        }
    }
}