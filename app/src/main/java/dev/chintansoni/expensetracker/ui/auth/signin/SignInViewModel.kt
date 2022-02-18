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
import kotlinx.coroutines.launch

class SignInViewModel(
    private val userRepository: UserRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

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

    fun onSignInClick(email: String, password: String, result: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            println("Logged in using $email / $password")
            userRepository.setUserLoggedIn(true)
            insertDefaultCategories()
            result()
        }
    }

    private suspend fun insertDefaultCategories() {
        val defaultCategories = generateDefaultCategories()
        categoryRepository.addCategories(defaultCategories)
    }
}