package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

class ForgotPasswordViewModel :
    BaseViewModel<ForgotPasswordContract.Event, ForgotPasswordContract.State, ForgotPasswordContract.Effect>() {

    override fun createInitialState(): ForgotPasswordContract.State =
        ForgotPasswordContract.State.default()

    override fun handleEvent(event: ForgotPasswordContract.Event) {
        when (event) {
            is ForgotPasswordContract.Event.OnEmailChange -> {
                setState {
                    copy(
                        email = event.email,
                        emailError = ""
                    )
                }
            }
            is ForgotPasswordContract.Event.OnSendLinkClick -> {
                sendPasswordResetLink()
            }
            is ForgotPasswordContract.Event.OnBackClick -> {
                setEffect { ForgotPasswordContract.Effect.NavigateBack }
            }
            is ForgotPasswordContract.Event.OnTryAgainClick -> {
                setState { copy(forgotPasswordApiState = ForgotPasswordContract.ForgotPasswordApiState.Idle) }
            }
        }
    }

    private fun sendPasswordResetLink() {
        setState {
            copy(
                emailError = currentState.validateEmail()
            )
        }
        launchInIO {
            if (currentState.isFormStateValid()) {
                setState { copy(forgotPasswordApiState = ForgotPasswordContract.ForgotPasswordApiState.InProgress) }
                delay(1500)
                if (Random.nextBoolean()) {
                    setState { copy(forgotPasswordApiState = ForgotPasswordContract.ForgotPasswordApiState.Success()) }
                } else {
                    setState { copy(forgotPasswordApiState = ForgotPasswordContract.ForgotPasswordApiState.Failure()) }
                }
            }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}