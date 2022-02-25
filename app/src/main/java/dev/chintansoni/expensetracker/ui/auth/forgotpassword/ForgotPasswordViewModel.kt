package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

class ForgotPasswordViewModel : ViewModel() {

    private val _emailMSF: MutableStateFlow<String> = MutableStateFlow("")
    val emailSF: StateFlow<String> = _emailMSF.asStateFlow()

    private val _sendLinkStatusMSF: MutableStateFlow<SendLinkStatus> =
        MutableStateFlow(SendLinkStatus.Default)
    val sendLinkStatusSF: StateFlow<SendLinkStatus> = _sendLinkStatusMSF.asStateFlow()

    fun setEmail(email: String) {
        _emailMSF.update { email }
    }

    fun sendLink() {
        _sendLinkStatusMSF.update { SendLinkStatus.InProgress }
        viewModelScope.launch {
            delay(1000)
            _sendLinkStatusMSF.update {
                if (Random.nextBoolean()) {
                    SendLinkStatus.Success
                } else {
                    SendLinkStatus.Failure
                }
            }
        }
    }

    fun resetSendLinkStatus() {
        _sendLinkStatusMSF.update { SendLinkStatus.Default }
    }
}

sealed class SendLinkStatus {
    object Default : SendLinkStatus()
    object InProgress : SendLinkStatus()
    object Success : SendLinkStatus()
    object Failure : SendLinkStatus()
}