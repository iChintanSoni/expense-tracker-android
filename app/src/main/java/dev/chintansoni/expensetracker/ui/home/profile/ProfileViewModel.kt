package dev.chintansoni.expensetracker.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.preference.LoginPreferenceDataStore
import kotlinx.coroutines.launch

class ProfileViewModel(private val loginPreferenceDataStore: LoginPreferenceDataStore) :
    ViewModel() {

    fun logout() {
        viewModelScope.launch {
            loginPreferenceDataStore.setUserLoggedIn(false)
        }
    }

}