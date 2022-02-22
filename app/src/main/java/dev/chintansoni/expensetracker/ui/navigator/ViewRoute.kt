package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavController
import androidx.navigation.NavOptions

sealed class ViewRoute(
    open val route: String,
    open val navOptions: NavOptions = NavOptions.Builder().build(),
    open val args: Map<String, Any> = emptyMap()
)

private const val ROUTE_BACK = "back"

object BackViewRoute : ViewRoute(ROUTE_BACK)

fun NavController.navigate(viewRoute: ViewRoute) {
    if (viewRoute is BackViewRoute) {
        navigateUp()
    } else {
        navigate(viewRoute.route, viewRoute.navOptions)
    }
}