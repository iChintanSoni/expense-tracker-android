package dev.chintansoni.database

import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

val databaseModule = module {
    single {
        provideExpenseTrackerDatabase(get())
    }
    single {
        getDatabase().transactionDao()
    }
    single {
        getDatabase().categoryDao()
    }
}

val testDatabaseModule = module {
    single {
        provideTestExpenseTrackerDatabase(get())
    }
    single {
        getDatabase().transactionDao()
    }
    single {
        getDatabase().categoryDao()
    }
}

private fun Scope.getDatabase() = get<ExpenseTrackerDatabase>()

val inMemoryDatabaseQualifier = named("InMemoryDatabase")
private fun Scope.getInMemoryDatabase() = get<ExpenseTrackerDatabase>(inMemoryDatabaseQualifier)