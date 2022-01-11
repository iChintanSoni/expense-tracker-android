package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavOptions
import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ROUTE_FORGOT_PASSWORD
import dev.chintansoni.expensetracker.ui.auth.signin.ROUTE_SIGN_IN
import dev.chintansoni.expensetracker.ui.auth.signup.ROUTE_SIGN_UP
import dev.chintansoni.expensetracker.ui.expense.ROUTE_ADD_EDIT_EXPENSE
import dev.chintansoni.expensetracker.ui.home.ROUTE_HOME
import dev.chintansoni.expensetracker.ui.splash.ROUTE_SPLASH
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainNavigator : Navigator<MainRoute> {
    private val _viewRouteStateFlow: MutableStateFlow<MainRoute?> = MutableStateFlow(null)
    override val viewRouteStateFlow: StateFlow<MainRoute?> by lazy {
        _viewRouteStateFlow.asStateFlow()
    }

    override fun navigate(viewRoute: MainRoute?) {
        _viewRouteStateFlow.value = viewRoute
    }
}

sealed class MainRoute(
    override val route: String,
    override val navOptions: NavOptions = NavOptions.Builder().build(),
    override val args: Map<String, Any> = emptyMap()
) : ViewRoute(route, navOptions, args) {

    object SplashViewRoute : MainRoute("Splash")

    object SplashToSignInViewRoute :
        MainRoute(ROUTE_SIGN_IN, NavOptions.Builder().setPopUpTo(ROUTE_SPLASH, true).build())

    object SignUpViewRoute : MainRoute(ROUTE_SIGN_UP)

    object ForgotPasswordViewRoute : MainRoute(ROUTE_FORGOT_PASSWORD)

    object SplashToHomeViewRoute :
        MainRoute(ROUTE_HOME, NavOptions.Builder().setPopUpTo(ROUTE_SPLASH, true).build())

    object SignInToHomeViewRoute :
        MainRoute(ROUTE_HOME, NavOptions.Builder().setPopUpTo(ROUTE_SIGN_IN, true).build())

    object HomeToSignInViewRoute :
        MainRoute(ROUTE_SIGN_IN, NavOptions.Builder().setPopUpTo(ROUTE_HOME, true).build())

    object GoBackViewRoute : MainRoute("GoBack")

    object AddEditExpenseViewRoute : MainRoute(ROUTE_ADD_EDIT_EXPENSE)
}