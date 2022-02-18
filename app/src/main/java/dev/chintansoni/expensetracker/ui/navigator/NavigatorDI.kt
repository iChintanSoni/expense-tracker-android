package dev.chintansoni.expensetracker.ui.navigator

import org.koin.dsl.module

val navigatorModule = module {
    single {
        MainNavigator()
    }
}