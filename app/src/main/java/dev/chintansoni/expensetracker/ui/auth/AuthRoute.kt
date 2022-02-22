package dev.chintansoni.expensetracker.ui.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ForgotPasswordScreen
import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ROUTE_FORGOT_PASSWORD
import dev.chintansoni.expensetracker.ui.auth.signin.ROUTE_SIGN_IN
import dev.chintansoni.expensetracker.ui.auth.signin.SignInView
import dev.chintansoni.expensetracker.ui.auth.signup.ROUTE_SIGN_UP
import dev.chintansoni.expensetracker.ui.auth.signup.SignUpView

fun NavGraphBuilder.authRoute(navController: NavController) {
    composable(ROUTE_SIGN_IN) {
        SignInView(navController)
    }
    composable(ROUTE_SIGN_UP) {
        SignUpView(navController)
    }
    composable(ROUTE_FORGOT_PASSWORD) {
        ForgotPasswordScreen(navController)
    }
}