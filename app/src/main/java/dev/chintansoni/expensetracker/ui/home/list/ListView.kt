package dev.chintansoni.expensetracker.ui.home.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.model.generateDummyTransactions
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.viewModel

const val ROUTE_LIST = "List"

@Composable
fun ListView() {
    val listViewModel: ListViewModel by viewModel()
    val transactions: List<Transaction> by listViewModel.transactionsFlow.collectAsState(emptyList())
    ListContent(transactions)
}

@Composable
fun ListContent(transactions: List<Transaction> = generateDummyTransactions()) {
    if (transactions.isEmpty()) {
        NoTransactionsAvailable()
    } else {
        TransactionList()
    }
}

@Preview(showBackground = true)
@Composable
fun NoTransactionsAvailable() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "No transactions to show. Click + to add",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.7f),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TransactionList(transactionList: List<Transaction> = generateDummyTransactions()) {
    LazyColumn {
        items(
            items = transactionList
        ) { transaction ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
                        transaction.note ?: "",
                        style = Typography.subtitle1
                    )
                    Text(
                        "${transaction.category}",
                        style = Typography.caption
                    )
                }
                Text(
                    text = "\u20B9 " + transaction.amount,
                    style = Typography.overline,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .align(Alignment.CenterVertically)
                )
            }
            Divider()
        }
    }
}