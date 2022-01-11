package dev.chintansoni.expensetracker.ui.auth.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SignUpViewModel : ViewModel() {

    private val _firstNameMSF: MutableStateFlow<String> = MutableStateFlow("")
    val firstNameSF: StateFlow<String> = _firstNameMSF.asStateFlow()

    private val _lastNameMSF: MutableStateFlow<String> = MutableStateFlow("")
    val lastNameSF: StateFlow<String> = _lastNameMSF.asStateFlow()

    private val _emailMSF: MutableStateFlow<String> = MutableStateFlow("")
    val emailSF: StateFlow<String> = _emailMSF.asStateFlow()

    private val _mobileMSF: MutableStateFlow<String> = MutableStateFlow("")
    val mobileSF: StateFlow<String> = _mobileMSF.asStateFlow()

    fun setEmail(email: String) {
        _emailMSF.value = email
    }

    fun setFirstName(firstName: String) {
        _firstNameMSF.value = firstName
    }

    fun setLastName(lastName: String) {
        _lastNameMSF.value = lastName
    }

    fun setMobile(mobile: String) {
        _mobileMSF.value = mobile
    }
}