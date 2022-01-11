package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavOptions

//class HomeNavigator : Navigator<MainRoute.HomeRoute> {
//}
//
sealed class HomeRoute(
    override val route: String,
    override val navOptions: NavOptions = NavOptions.Builder().build(),
    override val args: Map<String, Any> = emptyMap()
) : ViewRoute(route, navOptions, args) {
    object SplashViewRoute : HomeRoute("Splash")
    object SignInViewRoute :
        HomeRoute("SignIn", NavOptions.Builder().setPopUpTo("Splash", true).build())

    object SignUpViewRoute : HomeRoute("SignUp")
    object ForgotPasswordViewRoute : HomeRoute("ForgotPasswordViewRoute")
    object HomeViewRoute :
        HomeRoute("Home", NavOptions.Builder().setPopUpTo("Splash", true).build())

    object GoBackViewRoute : HomeRoute("GoBack")
}