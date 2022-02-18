package dev.chintansoni.expensetracker.ui.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.CategoryIcon
import dev.chintansoni.expensetracker.ui.theme.NavigateNextIcon
import dev.chintansoni.expensetracker.ui.util.MainToolbar
import org.koin.androidx.compose.inject

const val ROUTE_SETTING = "setting"

fun NavGraphBuilder.settingRoute() {
    composable(ROUTE_SETTING) {
        SettingView()
    }
}

@Composable
fun SettingView() {
    val mainNavigator: MainNavigator by inject()
    val onSettingOptionClick: (SettingOption) -> Unit = {
        when (it) {
            is Categories -> mainNavigator.navigate(MainRoute.CategoriesViewRoute)
        }
    }
    val onBackClick: () -> Unit = {
        mainNavigator.navigate(MainRoute.GoBackViewRoute())
    }
    BackHandler { onBackClick() }
    SettingContent(onSettingOptionClick = onSettingOptionClick, onBackClick = onBackClick)
}

@Preview(showBackground = true)
@Composable
private fun SettingContent(
    settingOptionList: List<SettingOption> = listOf(Categories),
    onSettingOptionClick: (SettingOption) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            MainToolbar(title = "Settings", onBackClick = onBackClick)
        },
    ) {
        Column {
            settingOptionList.forEach { settingOption ->
                SettingItem(settingOption, onSettingOptionClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingItem(
    settingOption: SettingOption = Categories,
    onSettingOptionClick: (SettingOption) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .clickable { onSettingOptionClick(settingOption) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CategoryIcon()
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(1f)
        ) {
            Text(text = settingOption.title, style = MaterialTheme.typography.subtitle1)
            Text(
                text = settingOption.subTitle,
                style = MaterialTheme.typography.caption,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        NavigateNextIcon()
    }
}

sealed class SettingOption(val title: String, val subTitle: String)
object Categories : SettingOption("Categories", "Manage categories to choose from")