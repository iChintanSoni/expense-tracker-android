package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ForgotPasswordViewModel : ViewModel() {

    private val _emailMSF: MutableStateFlow<String> = MutableStateFlow("")
    val emailSF: StateFlow<String> = _emailMSF.asStateFlow()

    private val _sendLinkStatusMSF: MutableStateFlow<SendLinkStatus> =
        MutableStateFlow(SendLinkStatus.Default)
    val sendLinkStatusMSF: StateFlow<SendLinkStatus> = _sendLinkStatusMSF.asStateFlow()

    fun setEmail(email: String) {
        _emailMSF.value = email
    }

    fun sendLink() {
        _sendLinkStatusMSF.value = SendLinkStatus.InProgress
        viewModelScope.launch {
            delay(3000)
            _sendLinkStatusMSF.value = SendLinkStatus.Success
        }
    }

    override fun onCleared() {
        super.onCleared()
        println("ForgotPassword ViewModel cleared")
    }
}

sealed class SendLinkStatus {
    object Default : SendLinkStatus()
    object InProgress : SendLinkStatus()
    object Success : SendLinkStatus()
}