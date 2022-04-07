package dev.chintansoni.expensetracker.ui.home.profile

import dev.chintansoni.domain.repository.UserRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlin.coroutines.CoroutineContext

class ProfileViewModel(private val userRepository: UserRepository) :
    BaseViewModel<ProfileViewContract.Event, ProfileViewContract.State, ProfileViewContract.Effect>() {

    override fun createInitialState(): ProfileViewContract.State =
        ProfileViewContract.State.default()

    override fun handleEvent(event: ProfileViewContract.Event) {
        when (event) {
            ProfileViewContract.Event.OnLogoutClick -> {
                performLogout()
            }
        }
    }

    private fun performLogout() {
        launchInIO {
            userRepository.setUserLoggedIn(false)
            setEffect { ProfileViewContract.Effect.NavigateToSignIn }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}