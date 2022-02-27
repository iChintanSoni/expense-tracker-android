package dev.chintansoni.expensetracker.ui.navigator

import androidx.navigation.NavOptions
import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ROUTE_FORGOT_PASSWORD
import dev.chintansoni.expensetracker.ui.auth.signin.ROUTE_SIGN_IN
import dev.chintansoni.expensetracker.ui.auth.signup.ROUTE_SIGN_UP
import dev.chintansoni.expensetracker.ui.category.detail.buildCategoryDetailRoute
import dev.chintansoni.expensetracker.ui.category.list.ROUTE_CATEGORIES
import dev.chintansoni.expensetracker.ui.home.ROUTE_HOME
import dev.chintansoni.expensetracker.ui.setting.ROUTE_SETTING
import dev.chintansoni.expensetracker.ui.splash.ROUTE_SPLASH
import dev.chintansoni.expensetracker.ui.transaction.detail.transactionDetailRoute

sealed class MainRoute(
    override val route: String,
    override val navOptions: NavOptions = NavOptions.Builder().build(),
    override val args: Map<String, Any> = emptyMap()
) : ViewRoute(route, navOptions, args) {

    object SplashViewRoute : MainRoute(ROUTE_SPLASH)

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

    data class TransactionDetailViewRoute(val transactionId: Long) :
        MainRoute(transactionDetailRoute(transactionId))

    object SettingViewRoute : MainRoute(ROUTE_SETTING)

    object CategoriesViewRoute : MainRoute(ROUTE_CATEGORIES)

    data class CategoryDetailViewRoute(val categoryId: Int = 0) :
        MainRoute(buildCategoryDetailRoute(categoryId))
}