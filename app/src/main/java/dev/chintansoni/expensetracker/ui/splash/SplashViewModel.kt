package dev.chintansoni.expensetracker.ui.splash

import androidx.lifecycle.ViewModel
import dev.chintansoni.preference.LoginPreferenceDataStore

class SplashViewModel(loginPreferenceDataStore: LoginPreferenceDataStore) :
    ViewModel() {

    val isUserLoggedInFlow = loginPreferenceDataStore.isUserLoggedIn

}