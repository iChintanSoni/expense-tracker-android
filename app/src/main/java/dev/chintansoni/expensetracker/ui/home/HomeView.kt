package dev.chintansoni.expensetracker.ui.home

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.home.chart.ROUTE_CHART
import dev.chintansoni.expensetracker.ui.home.list.ROUTE_LIST
import dev.chintansoni.expensetracker.ui.home.profile.ROUTE_PROFILE
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.AddIcon
import dev.chintansoni.expensetracker.ui.theme.ChartIcon
import dev.chintansoni.expensetracker.ui.theme.ListIcon
import dev.chintansoni.expensetracker.ui.theme.PersonIcon
import dev.chintansoni.expensetracker.ui.theme.SettingIcon
import dev.chintansoni.expensetracker.ui.util.Fab

const val ROUTE_HOME = "home"

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable(ROUTE_HOME) {
        HomeView(navController)
    }
}

@Composable
fun HomeView(navController: NavController = rememberNavController()) {
    val onSettingClick: () -> Unit = {
        navController.navigate(MainRoute.SettingViewRoute)
    }
    val onAddClick: () -> Unit = {
        navController.navigate(MainRoute.TransactionDetailViewRoute(0))
    }
    HomeContent(mainNavController = navController, onSettingClick = onSettingClick, onAddClick)
}

@Preview(showBackground = true)
@Composable
private fun HomeContent(
    mainNavController: NavController = rememberNavController(),
    onSettingClick: () -> Unit = {},
    onAddClick: () -> Unit = {}
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Expense Tracker") },
                actions = {
                    IconButton(onClick = onSettingClick) {
                        SettingIcon()
                    }
                }
            )
        },
        floatingActionButton = {
            Fab(AddIcon, onClick = onAddClick)
        },
        bottomBar = {
            BottomNavigation(navController)
        }
    ) { innerPadding ->
        NavigationGraph(innerPadding, navController, mainNavController)
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
    BottomNavigation(
        modifier = Modifier
            .graphicsLayer {
                shape = RoundedCornerShape(topStartPercent = 50, topEndPercent = 50)
                clip = true
            }
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        navItems.forEach { screen ->
            BottomNavigationItem(
                icon = screen.icon,
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
    navController: NavHostController = rememberNavController(),
    mainNavController: NavController = rememberNavController()
) {
    NavHost(
        navController,
        startDestination = NavItem.ChartNavItem.route,
        Modifier.padding(innerPadding)
    ) {
        homeContentRoute(mainNavController)
    }
}

sealed class NavItem(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object ChartNavItem : NavItem(ROUTE_CHART, "Chart", ChartIcon)
    object ListNavItem : NavItem(ROUTE_LIST, "List", ListIcon)
    object ProfileNavItem : NavItem(ROUTE_PROFILE, "Profile", PersonIcon)
}