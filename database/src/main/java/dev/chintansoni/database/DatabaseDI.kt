package dev.chintansoni.database

import org.koin.core.scope.Scope
import org.koin.dsl.module

val databaseModule = module {
    single {
        provideExpenseTrackerDatabase(get())
    }
    single {
        getDatabase().transactionDao()
    }
}

val testDatabaseModule = module {
    single {
        provideTestExpenseTrackerDatabase(get())
    }
    single {
        getDatabase().transactionDao()
    }
}

private fun Scope.getDatabase() = get<ExpenseTrackerDatabase>()