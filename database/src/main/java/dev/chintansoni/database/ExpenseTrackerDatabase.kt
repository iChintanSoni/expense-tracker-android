package dev.chintansoni.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.chintansoni.database.entity.category.CategoryDao
import dev.chintansoni.database.entity.category.CategoryEntity
import dev.chintansoni.database.entity.transaction.TransactionDao
import dev.chintansoni.database.entity.transaction.TransactionEntity

private const val DATABASE_VERSION = 1
private const val DATABASE_NAME = "ExpenseTrackerDatabase"

@Database(entities = [TransactionEntity::class, CategoryEntity::class], version = DATABASE_VERSION)
abstract class ExpenseTrackerDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
}

fun provideExpenseTrackerDatabase(context: Context): ExpenseTrackerDatabase {
    return Room.databaseBuilder(
        context,
        ExpenseTrackerDatabase::class.java,
        DATABASE_NAME
    ).build()
}

fun provideTestExpenseTrackerDatabase(context: Context): ExpenseTrackerDatabase {
    return Room.inMemoryDatabaseBuilder(
        context,
        ExpenseTrackerDatabase::class.java
    ).build()
}