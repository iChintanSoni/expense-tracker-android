package dev.chintansoni.domain

import dev.chintansoni.domain.usecase.LogoutUseCase
import org.koin.dsl.module

val domainModule = module {
    single {
        LogoutUseCase(get(), get())
    }
}