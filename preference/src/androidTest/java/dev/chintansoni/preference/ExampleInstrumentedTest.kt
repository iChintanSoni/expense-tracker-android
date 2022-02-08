package dev.chintansoni.preference

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest : KoinTest {

    private val loginPreferenceDataStore: LoginPreferenceDataStore by inject()

    @Before
    fun setUp() {
        stopKoin()
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        startKoin {
            androidContext(appContext.applicationContext)
            modules(preferenceModule)
        }
    }

    @Test
    fun testInitial() {
        runBlocking {
            assertEquals(false, loginPreferenceDataStore.isUserLoggedIn.first())
        }
    }

    @Test
    fun testAfterSettingPreference() {
        runBlocking {
            loginPreferenceDataStore.setUserLoggedIn(true)
            assertEquals(true, loginPreferenceDataStore.isUserLoggedIn.first())
        }
    }

    @Test
    fun testAfterClear() {
        runBlocking {
            loginPreferenceDataStore.clear()
            assertEquals(false, loginPreferenceDataStore.isUserLoggedIn.first())
        }
    }
}