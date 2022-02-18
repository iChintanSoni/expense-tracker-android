package dev.chintansoni.domain

import dev.chintansoni.domain.usecase.CategoryUseCase
import dev.chintansoni.domain.usecase.LogoutUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        LogoutUseCase(
            userRepository = get(),
            transactionRepository = get()
        )
    }
    single {
        CategoryUseCase(
            transactionRepository = get(),
            categoryRepository = get()
        )
    }
}