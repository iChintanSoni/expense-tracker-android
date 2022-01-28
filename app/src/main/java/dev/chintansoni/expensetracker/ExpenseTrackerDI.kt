package dev.chintansoni.expensetracker

import android.app.Application
import dev.chintansoni.domain.domainModule
import dev.chintansoni.expensetracker.ui.uiModules
import dev.chintansoni.repository.repositoryModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

fun Application.startKoinDI() {

    // Register new DI modules here
    val moduleList: List<Module> = mutableListOf(
        domainModule
    ).apply {
        addAll(uiModules)
        addAll(repositoryModules)
    }

    startKoin {
        androidContext(this@startKoinDI)
        androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
        modules(moduleList)
    }
}