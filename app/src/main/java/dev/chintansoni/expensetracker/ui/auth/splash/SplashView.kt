package dev.chintansoni.expensetracker.ui.auth.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.navigator.MainViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import org.koin.androidx.compose.viewModel

const val ROUTE_SPLASH = "SplashView"

@Composable
fun SplashView(navController: NavController = rememberNavController()) {
    val splashViewModel by viewModel<SplashViewModel>()
    splashViewModel.setEvent(SplashContract.Event.CheckLoginStatus)

    val effect by splashViewModel.effect.collectAsState(initial = SplashContract.Effect.Nothing)

    LaunchedEffect(key1 = effect) {
        when (effect) {
            SplashContract.Effect.NavigateToHome -> {
                navController.navigate(MainViewRoute.SplashToHomeViewRoute)
            }
            SplashContract.Effect.NavigateToSignIn -> {
                navController.navigate(MainViewRoute.SplashToSignInViewRoute)
            }
            else -> {}
        }
    }
}