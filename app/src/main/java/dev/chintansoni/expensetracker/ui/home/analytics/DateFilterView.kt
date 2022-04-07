package dev.chintansoni.expensetracker.ui.home.analytics

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.chintansoni.expensetracker.ui.theme.EventIcon

@Composable
fun RowScope.DateFilterView(
    selectedDateFilter: DateFilter = DateFilter.ThisMonth(),
    onDateFilterSelected: (DateFilter) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }
    val dateFilterList = listOf(
        DateFilter.Today(),
        DateFilter.ThisWeek(),
        DateFilter.ThisMonth(),
        DateFilter.ThisYear()
    )

    val onExpandChange: (Boolean) -> Unit = {
        expanded = it
    }
    DateFilterContent(
        expand = expanded,
        onExpandChange = onExpandChange,
        selectedDateFilter = selectedDateFilter,
        onDateFilterSelected = onDateFilterSelected,
        dateFilterList = dateFilterList
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
private fun RowScope.DateFilterContent(
    expand: Boolean = false,
    onExpandChange: (Boolean) -> Unit = {},
    selectedDateFilter: DateFilter = DateFilter.ThisMonth(),
    onDateFilterSelected: (DateFilter) -> Unit = {},
    dateFilterList: List<DateFilter> = emptyList()
) {

    ExposedDropdownMenuBox(
        expanded = expand,
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f),
        onExpandedChange = onExpandChange
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            readOnly = true,
            value = selectedDateFilter.label,
            singleLine = true,
            onValueChange = { },
            label = { Text("Filter") },
            leadingIcon = EventIcon,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expand
                )
            },
        )
        ExposedDropdownMenu(
            expanded = expand,
            onDismissRequest = {
                onExpandChange(false)
            }
        ) {
            dateFilterList.forEach { dateFilter ->
                DropdownMenuItem(
                    onClick = {
                        onDateFilterSelected(dateFilter)
                        onExpandChange(false)
                    }
                ) {
                    Text(
                        text = dateFilter.label
                    )
                }
            }
        }
    }
}

sealed class DateFilter(open val label: String) {
    data class Today(override val label: String = "Today") : DateFilter(label)
    data class ThisWeek(override val label: String = "This Week") : DateFilter(label)
    data class ThisMonth(override val label: String = "This Month") : DateFilter(label)
    data class ThisYear(override val label: String = "This Year") : DateFilter(label)
}