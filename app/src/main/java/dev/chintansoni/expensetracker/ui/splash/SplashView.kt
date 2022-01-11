package dev.chintansoni.expensetracker.ui.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import kotlinx.coroutines.delay
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

const val ROUTE_SPLASH = "SplashView"

@Composable
fun SplashView() {
    val mainNavigator: MainNavigator by inject()
    val splashViewModel by viewModel<SplashViewModel>()
    val isUserLoggedIn by splashViewModel.isUserLoggedInFlow.collectAsState(initial = false)
    var showGetStarted by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        delay(1500)
        if (isUserLoggedIn) {
            mainNavigator.navigate(MainRoute.SplashToHomeViewRoute)
        } else {
            showGetStarted = true
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome to Expense Tracker")

        AnimatedVisibility(visible = showGetStarted) {
            Button(
                onClick = {
                    mainNavigator.navigate(MainRoute.SplashToSignInViewRoute)
                }, modifier = Modifier
                    .padding(12.dp)
                    .width(280.dp)
            ) {
                Text("Sign In", modifier = Modifier.padding(4.dp))
            }
        }
    }
}