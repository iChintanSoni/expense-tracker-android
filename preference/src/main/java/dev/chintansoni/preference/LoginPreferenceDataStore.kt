package dev.chintansoni.preference

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.loginPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "LoginPreferencesDataStore")

private val isUserLoggedInPreference: Preferences.Key<Boolean> =
    booleanPreferencesKey("isUserLoggedIn")

class LoginPreferenceDataStore(private val context: Context) {

    val isUserLoggedIn: Flow<Boolean> =
        context.loginPreferencesDataStore.data.map {
            it[isUserLoggedInPreference] ?: false
        }

    suspend fun setUserLoggedIn(userLoggedIn: Boolean): Preferences {
        return context.loginPreferencesDataStore.edit { settings ->
            settings[isUserLoggedInPreference] = userLoggedIn
        }
    }

    suspend fun clear(): Preferences {
        return context.loginPreferencesDataStore.edit { settings ->
            settings.clear()
        }
    }
}
