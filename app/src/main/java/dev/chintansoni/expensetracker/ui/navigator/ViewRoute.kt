package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavOptions

sealed class ViewRoute(
    open val route: String,
    open val navOptions: NavOptions = NavOptions.Builder().build(),
    open val args: Map<String, Any> = emptyMap()
)