package dev.chintansoni.expensetracker.ui.navigator

import kotlinx.coroutines.flow.StateFlow

interface Navigator<T : ViewRoute> {

    val viewRouteStateFlow: StateFlow<T?>

    fun navigate(viewRoute: T?)
}