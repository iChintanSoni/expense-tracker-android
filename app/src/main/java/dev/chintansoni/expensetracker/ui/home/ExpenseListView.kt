package dev.chintansoni.expensetracker.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

const val ROUTE_LIST = "List"

@Composable
fun ExpenseListView() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(
            text = "List View",
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )
    }
//    LazyColumn {
////        items(
////            items = transactions,
////            key = {
////                it.id
////            }
////        ) { transaction ->
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .wrapContentHeight()
////                    .padding(vertical = 25.dp),
////                horizontalArrangement = Arrangement.Center,
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                Text(
////                    "\uD83C\uDF3F " + transaction.amount,
////                    style = MaterialTheme.typography.h3
////                )
////            }
////        }
//    }
}

class ListComposableViewModel : ViewModel() {

}