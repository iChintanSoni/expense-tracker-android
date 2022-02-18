package dev.chintansoni.expensetracker.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    fun logout(result: () -> Unit = {}) {
        viewModelScope.launch {
            userRepository.setUserLoggedIn(false)
            result()
        }
    }

}