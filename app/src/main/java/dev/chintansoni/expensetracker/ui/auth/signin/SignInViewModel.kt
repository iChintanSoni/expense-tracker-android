package dev.chintansoni.expensetracker.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.domain.model.generateDefaultCategories
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignInViewModel(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _emailMSF: MutableStateFlow<String> = MutableStateFlow("")
    val emailSF: StateFlow<String> = _emailMSF.asStateFlow()

    private val _passwordMSF: MutableStateFlow<String> = MutableStateFlow("")
    val passwordSF: StateFlow<String> = _passwordMSF.asStateFlow()

    fun setEmail(email: String) {
        _emailMSF.update { email }
    }

    fun setPassword(password: String) {
        _passwordMSF.update { password }
    }

    fun onSignInClick(email: String, password: String, result: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            println("Email: $email & Password: $password")
            userRepository.setUserLoggedIn(true)
            insertDefaultCategories()
            withContext(Dispatchers.Main) {
                result()
            }
        }
    }

    private suspend fun insertDefaultCategories() {
        val defaultCategories = generateDefaultCategories()
        categoryRepository.addCategories(defaultCategories)
    }
}