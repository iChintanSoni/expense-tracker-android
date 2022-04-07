package dev.chintansoni.repository.impl

import dev.chintansoni.domain.repository.UserRepository
import dev.chintansoni.preference.LoginPreferenceDataStore
import kotlinx.coroutines.flow.Flow

internal class UserRepositoryImpl(private val loginPreferenceDataStore: LoginPreferenceDataStore) :
    UserRepository {

    override fun isUserLoggedIn(): Flow<Boolean> = loginPreferenceDataStore.isUserLoggedIn

    override suspend fun setUserLoggedIn(loggedIn: Boolean) {
        loginPreferenceDataStore.setUserLoggedIn(loggedIn)
    }
}