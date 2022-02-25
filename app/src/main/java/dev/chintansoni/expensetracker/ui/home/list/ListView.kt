package dev.chintansoni.expensetracker.ui.home.list

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.transaction.list.TransactionsView

const val ROUTE_LIST = "List"

@Preview(showBackground = true)
@Composable
fun ListView(mainNavController: NavController = rememberNavController()) {
    TransactionsView(mainNavController)
}