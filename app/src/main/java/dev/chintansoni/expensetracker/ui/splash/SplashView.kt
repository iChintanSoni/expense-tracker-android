package dev.chintansoni.expensetracker.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

const val ROUTE_SPLASH = "SplashView"

@Composable
fun SplashView() {
    val mainNavigator: MainNavigator by inject()
    val splashViewModel by viewModel<SplashViewModel>()
    LaunchedEffect(key1 = true) {
        splashViewModel.isUserLoggedInFlow.collect {
            if (it) {
                mainNavigator.navigate(MainRoute.SplashToHomeViewRoute)
            } else {
                mainNavigator.navigate(MainRoute.SplashToSignInViewRoute)
            }
        }
    }
    SplashContent()
}

@Composable
fun SplashContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(0.5f),
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "App Logo"
        )
    }
}

@Preview
@Composable
fun ComposablePreview() {
    SplashContent()
}