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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.auth.authRoute
import dev.chintansoni.expensetracker.ui.expense.AddEditExpenseScreen
import dev.chintansoni.expensetracker.ui.expense.ROUTE_ADD_EDIT_EXPENSE
import dev.chintansoni.expensetracker.ui.home.HomeView
import dev.chintansoni.expensetracker.ui.home.ROUTE_HOME
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.splash.ROUTE_SPLASH
import dev.chintansoni.expensetracker.ui.splash.SplashView
import dev.chintansoni.expensetracker.ui.theme.ExpenseTrackerTheme
import org.koin.androidx.compose.inject

class ExpenseTrackerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
            println("MainRoute: $it")
            if (it == MainRoute.GoBackViewRoute) {
                navController.navigateUp()
            } else {
                navController.navigate(it.route, it.navOptions)
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = ROUTE_SPLASH
    ) {
        composable(ROUTE_SPLASH) {
            SplashView()
        }
        authRoute()
        composable(ROUTE_HOME) {
            HomeView()
        }
        composable(ROUTE_ADD_EDIT_EXPENSE) {
            AddEditExpenseScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ExpenseTrackerTheme {
        App()
    }
}