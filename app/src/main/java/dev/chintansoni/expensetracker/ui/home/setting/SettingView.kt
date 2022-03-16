package dev.chintansoni.expensetracker.ui.home.setting

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.navigator.navigateBack
import dev.chintansoni.expensetracker.ui.theme.CategoryIcon
import dev.chintansoni.expensetracker.ui.theme.NavigateNextIcon
import dev.chintansoni.expensetracker.ui.util.MainToolbar
import org.koin.androidx.compose.viewModel

const val ROUTE_SETTING = "setting"

fun NavGraphBuilder.settingRoute(navController: NavController) {
    composable(ROUTE_SETTING) {
        SettingView(navController)
    }
}

@Composable
fun SettingView(navController: NavController = rememberNavController()) {

    val viewModel: SettingViewModel by viewModel()
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = SettingViewContract.Effect.Nothing)
    LaunchedEffect(key1 = effect, block = {
        when (effect) {
            is SettingViewContract.Effect.NavigateToSettingOption -> {
                when ((effect as SettingViewContract.Effect.NavigateToSettingOption).settingOption) {
                    SettingOption.Categories -> {
                        navController.navigate(MainRoute.CategoriesViewRoute)
                    }
                }
            }
            SettingViewContract.Effect.NavigateBack -> {
                navController.navigateBack()
            }
            else -> {}
        }
    })

    val onSettingOptionClick: (SettingOption) -> Unit = {
        viewModel.setEvent(SettingViewContract.Event.OnSettingOptionClick(it))
    }
    val onBackClick: () -> Unit = {
        viewModel.setEvent(SettingViewContract.Event.OnBackClick)
    }
    BackHandler { onBackClick() }
    SettingContent(
        state = state,
        onSettingOptionClick = onSettingOptionClick,
        onBackClick = onBackClick
    )
}

@Preview(showBackground = true)
@Composable
private fun SettingContent(
    state: SettingViewContract.State = SettingViewContract.State.default(),
    onSettingOptionClick: (SettingOption) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            MainToolbar(title = "Settings", onBackClick = onBackClick)
        },
    ) {
        Column {
            state.settingOptions.forEach { settingOption ->
                SettingItem(settingOption, onSettingOptionClick)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SettingItem(
    settingOption: SettingOption = SettingOption.Categories,
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