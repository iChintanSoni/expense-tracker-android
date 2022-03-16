package dev.chintansoni.database

import org.koin.core.scope.Scope
import org.koin.dsl.module

private val daoModule = module {
    single {
        getDatabase().transactionDao()
    }
    single {
        getDatabase().transactionDetailViewDao()
    }
    single {
        getDatabase().categoryDao()
    }
}

val databaseModule = module {
    single {
        provideExpenseTrackerDatabase(get())
    }
}.plus(daoModule)

val testDatabaseModule = module {
    single {
        provideTestExpenseTrackerDatabase(get())
    }
}.plus(daoModule)

private fun Scope.getDatabase() = get<ExpenseTrackerDatabase>()