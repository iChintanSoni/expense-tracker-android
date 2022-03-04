package dev.chintansoni.expensetracker.ui.auth.signin

import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.model.generateDefaultCategories
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.UserRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SignInViewModel(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) :
    BaseViewModel<SignInContract.Event, SignInContract.State, SignInContract.Effect>() {

    override fun createInitialState(): SignInContract.State = SignInContract.State.default()

    override fun handleEvent(event: SignInContract.Event) = when (event) {
        is SignInContract.Event.OnEmailChange -> {
            setState {
                copy(
                    email = event.email,
                    emailError = ""
                )
            }
        }
        is SignInContract.Event.OnPasswordChange -> {
            setState {
                copy(
                    password = event.password,
                    passwordError = ""
                )
            }
        }
        SignInContract.Event.OnSignInClick -> {
            performLogin()
        }
        SignInContract.Event.OnForgotPasswordClick -> {
            setEffect { SignInContract.Effect.NavigateToForgotPassword }
        }
        SignInContract.Event.OnSignUpClick -> {
            setEffect { SignInContract.Effect.NavigateToSignUp }
        }
    }

    private fun performLogin() {

        setState {
            copy(
                emailError = currentState.validateEmail(),
                passwordError = currentState.validatePassword()
            )
        }

        if (currentState.isFormStateValid()) {
            viewModelScope.launch {
                setState { copy(isLoading = true) }
                delay(1500)
                userRepository.setUserLoggedIn(true)
                insertDefaultCategories()
                setState { copy(isLoading = false) }
                setEffect { SignInContract.Effect.NavigateToHome }
            }
        }
    }

    private suspend fun insertDefaultCategories() {
        val defaultCategories = generateDefaultCategories()
        categoryRepository.addCategories(defaultCategories)
    }
}