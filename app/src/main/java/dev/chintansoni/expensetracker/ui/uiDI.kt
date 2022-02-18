package dev.chintansoni.expensetracker.ui

import dev.chintansoni.expensetracker.ui.auth.authModule
import dev.chintansoni.expensetracker.ui.category.categoryModule
import dev.chintansoni.expensetracker.ui.home.homeModule
import dev.chintansoni.expensetracker.ui.navigator.navigatorModule
import dev.chintansoni.expensetracker.ui.splash.splashModule

val uiModules = listOf(
    navigatorModule,
    splashModule,
    authModule,
    homeModule,
    categoryModule
)