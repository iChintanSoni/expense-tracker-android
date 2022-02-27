package dev.chintansoni.expensetracker.ui.auth.splash

import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.repository.UserRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.launch

class SplashViewModel(private val userRepository: UserRepository) :
    BaseViewModel<SplashContract.Event, SplashContract.State, SplashContract.Effect>() {

    override fun createInitialState(): SplashContract.State = SplashContract.State.default()

    override fun handleEvent(event: SplashContract.Event) {
        when (event) {
            SplashContract.Event.CheckLoginStatus -> {
                viewModelScope.launch {
                    userRepository.isUserLoggedIn().collect {
                        setState { copy(userLoggedIn = it) }
                        setEffect {
                            if (it) {
                                SplashContract.Effect.NavigateToHome
                            } else {
                                SplashContract.Effect.NavigateToSignIn
                            }
                        }
                    }
                }
            }
        }
    }

}