package dev.chintansoni.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserRepository {

    /**
     * Observes to see if User is logged in
     */
    fun isUserLoggedIn(): Flow<Boolean>

    /**
     * set User Logged in using [loggedIn]
     */
    suspend fun setUserLoggedIn(loggedIn: Boolean)
}