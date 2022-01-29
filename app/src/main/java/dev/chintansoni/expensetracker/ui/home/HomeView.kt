package dev.chintansoni.expensetracker.ui.home

import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.home.chart.ROUTE_CHART
import dev.chintansoni.expensetracker.ui.home.list.ROUTE_LIST
import dev.chintansoni.expensetracker.ui.home.profile.ROUTE_PROFILE
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.AddIcon
import dev.chintansoni.expensetracker.ui.theme.ChartIcon
import dev.chintansoni.expensetracker.ui.theme.ListIcon
import dev.chintansoni.expensetracker.ui.theme.PersonIcon
import org.koin.androidx.compose.inject

const val ROUTE_HOME = "home"

@Preview(showBackground = true)
@Composable
fun HomeView() {
    val navController = rememberNavController()
    Scaffold(
        floatingActionButton = {
            Fab()
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        NavigationGraph(innerPadding, navController)
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigation(
    navController: NavController = NavHostController(
        LocalContext.current
    )
) {
    val navItems = listOf(
        NavItem.ChartNavItem,
        NavItem.ListNavItem,
        NavItem.ProfileNavItem
    )
    BottomNavigation {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        navItems.forEach { screen ->
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

@Preview(showBackground = true)
@Composable
fun NavigationGraph(
    innerPadding: PaddingValues = PaddingValues(16.dp),
    navController: NavHostController = NavHostController(
        LocalContext.current
    )
) {
    NavHost(
        navController,
        startDestination = NavItem.ChartNavItem.route,
        Modifier.padding(innerPadding)
    ) {
        homeRoute()
    }
}

@Composable
fun Fab() {
    val mainNavigator: MainNavigator by inject()
    val fabShape = RoundedCornerShape(50)
    FloatingActionButton(
        onClick = { mainNavigator.navigate(MainRoute.AddEditExpenseViewRoute) },
        shape = fabShape
    ) {
        Icon(AddIcon, "Add Expense Icon")
    }
}

sealed class NavItem(val route: String, val label: String, val imageVector: ImageVector) {
    object ChartNavItem : NavItem(ROUTE_CHART, "Chart", ChartIcon)
    object ListNavItem : NavItem(ROUTE_LIST, "List", ListIcon)
    object ProfileNavItem : NavItem(ROUTE_PROFILE, "Profile", PersonIcon)
}