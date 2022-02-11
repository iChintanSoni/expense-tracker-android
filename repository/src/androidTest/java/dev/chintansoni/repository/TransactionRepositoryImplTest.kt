package dev.chintansoni.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
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

@RunWith(AndroidJUnit4::class)
class TransactionRepositoryImplTest : KoinTest {

    private val transactionRepository: TransactionRepository by inject()

    @Before
    fun setUp() {
        stopKoin()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        startKoin {
            androidContext(appContext.applicationContext)
            modules(repositoryModules)
        }
    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAllTransactionsFlow() {
        runBlocking {
            assertEquals(
                emptyList<Transaction>(),
                transactionRepository.getAllTransactionsFlow().first()
            )
        }
    }

    @Test
    fun getTransactionByIdFlow() {
        runBlocking {
            val expectedTransaction = Transaction.dummyInstance()
            val transactionId = transactionRepository.addTransaction(expectedTransaction)
            assert(transactionId > 0)
            assertEquals(
                expectedTransaction.copy(id = transactionId),
                transactionRepository.getTransactionByIdFlow(transactionId.toInt()).first()
            )
        }
    }

    @Test
    fun addTransaction() {
        runBlocking {
            val expectedTransaction = Transaction.dummyInstance()
            val transactionId = transactionRepository.addTransaction(expectedTransaction)
            assert(transactionId > 0)
            assertEquals(
                expectedTransaction.copy(id = transactionId),
                transactionRepository.getTransactionByIdFlow(transactionId.toInt()).first()
            )
        }
    }

    @Test
    fun updateTransaction() {
    }

    @Test
    fun deleteTransaction() {
    }

    @Test
    fun clear() {
    }
}