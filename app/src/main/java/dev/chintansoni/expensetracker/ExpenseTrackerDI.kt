package dev.chintansoni.expensetracker

import android.app.Application
import dev.chintansoni.expensetracker.ui.auth.authModule
import dev.chintansoni.expensetracker.ui.home.homeModule
import dev.chintansoni.expensetracker.ui.navigator.navigatorDI
import dev.chintansoni.expensetracker.ui.splash.splashModule
import dev.chintansoni.preference.preferenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

fun Application.startKoinDI() {

    // Register new DI modules here
    val moduleList: List<Module> = listOf(
        navigatorDI,
        splashModule,
        authModule,
        homeModule,
        preferenceModule
    )

    startKoin {
        androidContext(this@startKoinDI)
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        modules(moduleList)
    }
}