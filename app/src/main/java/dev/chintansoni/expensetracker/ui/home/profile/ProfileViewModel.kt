package dev.chintansoni.expensetracker.ui.home.profile

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import dev.chintansoni.domain.repository.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    @MainThread
    suspend fun logout() {
        userRepository.setUserLoggedIn(false)
    }

}