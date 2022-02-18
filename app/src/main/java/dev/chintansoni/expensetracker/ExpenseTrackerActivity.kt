package dev.chintansoni.expensetracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.auth.authRoute
import dev.chintansoni.expensetracker.ui.category.list.categoriesRoute
import dev.chintansoni.expensetracker.ui.category.manage.manageCategoryRoute
import dev.chintansoni.expensetracker.ui.home.homeRoute
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.setting.settingRoute
import dev.chintansoni.expensetracker.ui.splash.splashRoute
import dev.chintansoni.expensetracker.ui.theme.ExpenseTrackerTheme
import dev.chintansoni.expensetracker.ui.transactiondetail.transactionDetailRoute
import org.koin.androidx.compose.inject

class ExpenseTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            ExpenseTrackerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val mainNavigator: MainNavigator by inject()
    val viewRoute: MainRoute? by mainNavigator.viewRouteStateFlow.collectAsState()

    LaunchedEffect(key1 = viewRoute) {
        viewRoute?.let {
            if (it is MainRoute.GoBackViewRoute) {
                navController.navigateUp()
            } else {
                navController.navigate(it.route, it.navOptions)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = MainRoute.SplashViewRoute.route
    ) {
        splashRoute()
        authRoute()
        homeRoute()
        transactionDetailRoute()
        settingRoute()
        categoriesRoute()
        manageCategoryRoute()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpenseTrackerTheme {
        App()
    }
}