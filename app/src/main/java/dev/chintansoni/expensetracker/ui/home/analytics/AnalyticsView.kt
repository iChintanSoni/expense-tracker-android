package dev.chintansoni.expensetracker.ui.home.analytics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chintansoni.common.compactDecimalFormat
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.inject

const val ROUTE_CHART = "chart"

@Composable
fun AnalyticsView() {

    val viewModel by inject<AnalyticsViewModel>()
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = AnalyticsViewContract.Effect.Nothing)

    LaunchedEffect(key1 = effect) {
        when (effect) {
            AnalyticsViewContract.Effect.Nothing -> {}
        }
    }

    val onDateFilterSelected: (DateFilter) -> Unit = {
        viewModel.setEvent(event = AnalyticsViewContract.Event.OnDateFilterSelected(it))
    }

    AnalyticsContent(
        state = state,
        onDateFilterSelected = onDateFilterSelected
    )
}

@Preview(showBackground = true)
@Composable
fun AnalyticsContent(
    state: AnalyticsViewContract.State = AnalyticsViewContract.State.default(),
    onDateFilterSelected: (DateFilter) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {

        Row {
            DateFilterView(
                onDateFilterSelected = onDateFilterSelected,
                selectedDateFilter = state.selectedDateFilter
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Tile(heading = compactDecimalFormat(state.totalSpent), caption = "Total Spent")

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            items(items = state.categoryTotalList) { item ->
                CategorySpent(item, state.totalSpent)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ColumnScope.Tile(heading: String = "1.2K", caption: String = "Total Spent") {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = Modifier
            .align(Alignment.CenterHorizontally)
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Text(text = heading, style = Typography.h1)
            Text(text = caption, style = Typography.caption)
        }
    }
}

@Composable
fun CategorySpent(categoryAmount: Pair<String, Double>, totalSpent: Double) {
    Text(text = categoryAmount.first)
    Spacer(modifier = Modifier.height(4.dp))
    println((categoryAmount.second / totalSpent).toFloat())
    LinearProgressIndicator(
        progress = (categoryAmount.second / totalSpent).toFloat(),
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
}