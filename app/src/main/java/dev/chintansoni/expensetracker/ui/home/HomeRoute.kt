package dev.chintansoni.expensetracker.ui.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chintansoni.expensetracker.ui.home.chart.ChartView
import dev.chintansoni.expensetracker.ui.home.list.ListView
import dev.chintansoni.expensetracker.ui.home.profile.ProfileView

fun NavGraphBuilder.homeContentRoute(mainNavController: NavController) {
    composable(NavItem.ChartNavItem.route) { ChartView() }
    composable(NavItem.ListNavItem.route) { ListView(mainNavController) }
    composable(NavItem.ProfileNavItem.route) { ProfileView(mainNavController) }
}