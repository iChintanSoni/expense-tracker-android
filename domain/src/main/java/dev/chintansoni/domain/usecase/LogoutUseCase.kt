package dev.chintansoni.domain.usecase

import dev.chintansoni.domain.repository.TransactionRepository
import dev.chintansoni.domain.repository.UserRepository

class LogoutUseCase(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {

    suspend fun logout() {
        userRepository.setUserLoggedIn(false)
        transactionRepository.clear()
    }
}