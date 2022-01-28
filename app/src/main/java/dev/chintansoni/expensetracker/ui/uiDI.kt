package dev.chintansoni.expensetracker.ui

import dev.chintansoni.expensetracker.ui.auth.authModule
import dev.chintansoni.expensetracker.ui.home.homeModule
import dev.chintansoni.expensetracker.ui.navigator.navigatorDI
import dev.chintansoni.expensetracker.ui.splash.splashModule

val uiModules = listOf(
    navigatorDI,
    splashModule,
    authModule,
    homeModule
)