package dev.chintansoni.expensetracker.ui.transaction.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.chintansoni.domain.model.TransactionDetail
import dev.chintansoni.domain.model.generateDummyTransactions
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.navigator.MainViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.viewModel

@Composable
fun TransactionsView(mainNavController: NavController) {
    val viewModel: ListViewModel by viewModel()
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = ListViewContract.Effect.Nothing)

    val onTransactionClick: (TransactionDetail) -> Unit = {
        viewModel.setEvent(ListViewContract.Event.OnTransactionClick(transactionId = it.id))
    }

    LaunchedEffect(key1 = effect, block = {
        when (effect) {
            is ListViewContract.Effect.NavigateToDetail -> mainNavController.navigate(
                MainViewRoute.TransactionDetailViewRoute(
                    (effect as ListViewContract.Effect.NavigateToDetail).transactionId
                )
            )
            ListViewContract.Effect.Nothing -> {}
        }
    })
    TransactionsContent(state, onTransactionClick)
}

@Preview(showBackground = true)
@Composable
private fun TransactionsContent(
    state: ListViewContract.State = ListViewContract.State.dummy(),
    onTransactionClick: (TransactionDetail) -> Unit = {}
) {
    if (state.transactionDetailList.isEmpty()) {
        NoTransactionsAvailable()
    } else {
        TransactionList(state.transactionDetailList, onTransactionClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun NoTransactionsAvailable() {
    val maxWidth = 0.7f
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "No transactions to show. Click + to add",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(maxWidth),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun TransactionList(
    transactionList: List<TransactionDetail> = generateDummyTransactions(),
    onTransactionClick: (TransactionDetail) -> Unit = {}
) {
    LazyColumn {
        itemsIndexed(transactionList) { index, transaction ->
            TransactionItem(
                modifier = Modifier.animateItemPlacement(),
                transaction = transaction,
                onTransactionClick = onTransactionClick
            )
            if (index < transactionList.lastIndex)
                Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: TransactionDetail = TransactionDetail.dummyInstance(),
    onTransactionClick: (TransactionDetail) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onTransactionClick(transaction) }
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(36.dp)
                    .background(Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_category),
                    contentDescription = "",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Column(
            modifier = Modifier
                .weight(1f, true)
                .padding(vertical = 8.dp)
        ) {
            Text(
                text = transaction.note ?: "",
                style = Typography.subtitle1
            )
            Text(
                text = transaction.categoryName,
                style = Typography.caption
            )
        }
        Text(
            text = "\u20B9 " + transaction.amount,
            style = Typography.h6,
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
                .align(Alignment.CenterVertically)
        )
    }
}
