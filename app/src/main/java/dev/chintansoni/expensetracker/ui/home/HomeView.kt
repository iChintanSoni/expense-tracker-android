package dev.chintansoni.expensetracker.ui.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.home.profile.ProfileView
import dev.chintansoni.expensetracker.ui.home.profile.ROUTE_PROFILE
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.AddIcon
import dev.chintansoni.expensetracker.ui.theme.ChartIcon
import dev.chintansoni.expensetracker.ui.theme.ListIcon
import dev.chintansoni.expensetracker.ui.theme.PersonIcon
import kotlinx.datetime.Instant
import org.koin.androidx.compose.inject

const val ROUTE_HOME = "home"

@Composable
fun HomeView() {
    val fabShape = RoundedCornerShape(50)
    val homeRoutes = listOf(
        HomeRoute.ChartScreen,
        HomeRoute.ListScreen,
        HomeRoute.ProfileScreen
    )
    val navController = rememberNavController()
    val mainNavigator: MainNavigator by inject()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { mainNavigator.navigate(MainRoute.AddEditExpenseViewRoute) },
                shape = fabShape
            ) {
                Icon(AddIcon, "")
            }
        },
        bottomBar = {
            BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                homeRoutes.forEach { screen ->
                    BottomNavigationItem(
                        icon = { Icon(screen.imageVector, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = HomeRoute.ChartScreen.route,
            Modifier.padding(innerPadding)
        ) {
            composable(HomeRoute.ChartScreen.route) { ExpenseChartView() }
            composable(HomeRoute.ListScreen.route) { ExpenseListView() }
            composable(HomeRoute.ProfileScreen.route) { ProfileView() }
        }
    }
}

sealed class HomeRoute(val route: String, val label: String, val imageVector: ImageVector) {
    object ChartScreen : HomeRoute(ROUTE_CHART, "Chart", ChartIcon)
    object ListScreen : HomeRoute(ROUTE_LIST, "List", ListIcon)
    object ProfileScreen : HomeRoute(ROUTE_PROFILE, "Profile", PersonIcon)
}

sealed class TransactionType {
    object CreditTransactionType : TransactionType()
    object DebitTransactionType : TransactionType()
}

sealed class Transaction(
    open val id: Int,
    open val amount: Float,
    open val note: String,
    open val category: String,
    open val date: Instant,
    open val type: TransactionType
)

data class DebitTransaction(
    override val id: Int,
    override val amount: Float,
    override val note: String,
    override val category: String,
    override val date: Instant,
    override val type: TransactionType = TransactionType.DebitTransactionType
) :
    Transaction(id, amount, note, category, date, type)

data class CreditTransaction(
    override val id: Int,
    override val amount: Float,
    override val note: String,
    override val category: String,
    override val date: Instant,
    override val type: TransactionType = TransactionType.CreditTransactionType
) :
    Transaction(id, amount, note, category, date, type)