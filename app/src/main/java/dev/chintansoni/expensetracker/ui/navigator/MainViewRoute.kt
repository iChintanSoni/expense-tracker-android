package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavOptions
import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ROUTE_FORGOT_PASSWORD
import dev.chintansoni.expensetracker.ui.auth.signin.ROUTE_SIGN_IN
import dev.chintansoni.expensetracker.ui.auth.signup.ROUTE_SIGN_UP
import dev.chintansoni.expensetracker.ui.auth.splash.ROUTE_SPLASH
import dev.chintansoni.expensetracker.ui.category.detail.buildCategoryDetailRoute
import dev.chintansoni.expensetracker.ui.category.list.ROUTE_CATEGORIES
import dev.chintansoni.expensetracker.ui.home.ROUTE_HOME
import dev.chintansoni.expensetracker.ui.home.setting.ROUTE_SETTING
import dev.chintansoni.expensetracker.ui.transaction.detail.transactionDetailRoute

sealed class MainViewRoute(
    override val route: String,
    override val navOptions: NavOptions = NavOptions.Builder().build(),
    override val args: Map<String, Any> = emptyMap()
) : ViewRoute(route, navOptions, args) {

    object SplashViewRoute : MainViewRoute(ROUTE_SPLASH)

    object SplashToSignInViewRoute :
        MainViewRoute(ROUTE_SIGN_IN, NavOptions.Builder().setPopUpTo(ROUTE_SPLASH, true).build())

    object SignUpViewRoute : MainViewRoute(ROUTE_SIGN_UP)

    object ForgotPasswordViewRoute : MainViewRoute(ROUTE_FORGOT_PASSWORD)

    object SplashToHomeViewRoute :
        MainViewRoute(ROUTE_HOME, NavOptions.Builder().setPopUpTo(ROUTE_SPLASH, true).build())

    object SignInToHomeViewRoute :
        MainViewRoute(ROUTE_HOME, NavOptions.Builder().setPopUpTo(ROUTE_SIGN_IN, true).build())

    object HomeToSignInViewRoute :
        MainViewRoute(ROUTE_SIGN_IN, NavOptions.Builder().setPopUpTo(ROUTE_HOME, true).build())

    data class TransactionDetailViewRoute(val transactionId: Long) :
        MainViewRoute(transactionDetailRoute(transactionId))

    object SettingViewRoute : MainViewRoute(ROUTE_SETTING)

    object CategoriesViewRoute : MainViewRoute(ROUTE_CATEGORIES)

    data class CategoryDetailViewRoute(val categoryId: Long = 0) :
        MainViewRoute(buildCategoryDetailRoute(categoryId))
}