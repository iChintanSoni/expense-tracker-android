package dev.chintansoni.expensetracker.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.preference.LoginPreferenceDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewModel(private val loginPreferenceDataStore: LoginPreferenceDataStore) :
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

    fun setLoginPreference() {
        viewModelScope.launch {
            loginPreferenceDataStore.setUserLoggedIn(true)
        }
    }
}