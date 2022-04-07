package dev.chintansoni.repository

import dev.chintansoni.database.databaseModule
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.TransactionDetailRepository
import dev.chintansoni.domain.repository.TransactionRepository
import dev.chintansoni.domain.repository.UserRepository
import dev.chintansoni.preference.preferenceModule
import dev.chintansoni.repository.impl.CategoryRepositoryImpl
import dev.chintansoni.repository.impl.TransactionDetailRepositoryImpl
import dev.chintansoni.repository.impl.TransactionRepositoryImpl
import dev.chintansoni.repository.impl.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModules = module {
    single<UserRepository> {
        UserRepositoryImpl(get())
    }

    single<TransactionRepository> {
        TransactionRepositoryImpl(transactionDao = get())
    }

    single<TransactionDetailRepository> {
        TransactionDetailRepositoryImpl(transactionDetailDao = get())
    }

    single<CategoryRepository> {
        CategoryRepositoryImpl(get())
    }
}.plus(databaseModule).plus(preferenceModule)
