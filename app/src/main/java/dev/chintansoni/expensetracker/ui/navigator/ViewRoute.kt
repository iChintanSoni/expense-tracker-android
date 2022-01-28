package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavOptions

const val ROUTE_GO_BACK = "GoBack"

sealed class ViewRoute(
    open val route: String,
    open val navOptions: NavOptions = NavOptions.Builder().build(),
    open val args: Map<String, Any> = emptyMap()
) {
    object GoBackViewRoute : ViewRoute(ROUTE_GO_BACK)
}