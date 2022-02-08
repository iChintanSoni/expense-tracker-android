package dev.chintansoni.database

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.chintansoni.database.entity.transaction.TransactionDao
import dev.chintansoni.database.entity.transaction.TransactionEntity
import dev.chintansoni.database.entity.transaction.TransactionType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertNotNull

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : KoinTest {

    private val transactionDao: TransactionDao by inject()

    @Before
    fun setUp() {
        stopKoin()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        startKoin {
            androidContext(appContext.applicationContext)
            modules(testDatabaseModule)
        }
    }

    @Test
    fun testInsertTransaction() {
        val transactionEntityExpected = TransactionEntity(
            id = 0,
            amount = 10.0f,
            note = "Snacks",
            category = "Food",
            date = 1,
            type = TransactionType.Debit,
            createdDate = 1,
            updatedDate = 1,
            deletedDate = 1
        )
        runBlocking {
            val transactionId = transactionDao.insertTransaction(transactionEntityExpected)
            val transactionEntityActual: TransactionEntity? =
                transactionDao.getByIdFlow(transactionId.toInt()).first()
            assertEquals(
                transactionEntityExpected.copy(id = transactionId.toInt()),
                transactionEntityActual
            )
        }
    }

    @Test
    fun testUpdateTransaction() {
        val originalTransaction = TransactionEntity(
            id = 10,
            amount = 10.0f,
            note = "Today's Dinner",
            category = "Food",
            date = 1,
            type = TransactionType.Debit,
            createdDate = 1,
            updatedDate = 1,
            deletedDate = 1
        )
        runBlocking {
            val transactionId = transactionDao.insertTransaction(originalTransaction)
            assert(transactionId > 0) {
                "Insertion of Transaction failed."
            }

            val actualTransactionEntity: TransactionEntity? =
                transactionDao.getByIdFlow(transactionId.toInt()).first()
            assertNotNull(actualTransactionEntity) {
                val expectedUpdatedTransaction = it.copy(
                    category = "Electronics",
                    note = "Apple Watch"
                )
                val rowsAffected = transactionDao.updateTransaction(expectedUpdatedTransaction)
                assert(rowsAffected > 0) {
                    "Updation of Transaction failed."
                }
            }
        }
    }

    @Test
    fun testDeleteTransaction() {
        val originalTransaction = TransactionEntity(
            id = 10,
            amount = 10.0f,
            note = "Today's Dinner",
            category = "Food",
            date = 1,
            type = TransactionType.Debit,
            createdDate = 1,
            updatedDate = 1,
            deletedDate = 1
        )
        runBlocking {
            val transactionId = transactionDao.insertTransaction(originalTransaction)
            assert(transactionId > 0) {
                "Insertion of Transaction failed."
            }

            val actualTransactionEntity: TransactionEntity? =
                transactionDao.getByIdFlow(transactionId.toInt()).first()
            assertNotNull(actualTransactionEntity) {
                val rowsAffected = transactionDao.deleteTransaction(it)
                assert(rowsAffected > 0) {
                    "Deletion of Transaction failed."
                }
            }
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}