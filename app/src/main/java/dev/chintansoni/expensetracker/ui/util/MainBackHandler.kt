package dev.chintansoni.expensetracker.ui.util

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import org.koin.androidx.compose.inject

@Composable
fun MainBackHandler() {
    val mainNavigator: MainNavigator by inject()
    BackHandler {
        mainNavigator.navigate(MainRoute.GoBackViewRoute())
    }
}