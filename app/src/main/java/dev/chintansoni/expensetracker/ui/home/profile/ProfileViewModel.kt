package dev.chintansoni.expensetracker.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    fun logout(result: () -> Unit = {}) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.setUserLoggedIn(false)
            withContext(Dispatchers.Main) {
                result()
            }
        }
    }

}