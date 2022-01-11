package dev.chintansoni.expensetracker.ui.navigator

import org.koin.dsl.module

val navigatorDI = module {
    single {
        MainNavigator()
    }
}