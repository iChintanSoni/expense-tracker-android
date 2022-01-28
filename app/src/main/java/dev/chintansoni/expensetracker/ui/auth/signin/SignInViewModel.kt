package dev.chintansoni.expensetracker.ui.auth.signin

import androidx.lifecycle.ViewModel
import dev.chintansoni.domain.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignInViewModel(private val userRepository: UserRepository) :
    ViewModel() {

    private val _emailMSF: MutableStateFlow<String> = MutableStateFlow("")
    val emailSF: StateFlow<String> = _emailMSF.asStateFlow()

    private val _passwordMSF: MutableStateFlow<String> = MutableStateFlow("")
    val passwordSF: StateFlow<String> = _passwordMSF.asStateFlow()

    fun setEmail(email: String) {
        _emailMSF.value = email
    }

    fun setPassword(password: String) {
        _passwordMSF.value = password
    }

    suspend fun setLoginPreference() {
        userRepository.setUserLoggedIn(true)
    }
}