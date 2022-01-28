package dev.chintansoni.expensetracker.ui.splash

import androidx.lifecycle.ViewModel
import dev.chintansoni.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class SplashViewModel(userRepository: UserRepository) :
    ViewModel() {

    val isUserLoggedInFlow: Flow<Boolean> = userRepository.isUserLoggedIn()

}