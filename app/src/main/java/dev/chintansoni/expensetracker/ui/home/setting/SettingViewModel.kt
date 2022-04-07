package dev.chintansoni.expensetracker.ui.home.setting

import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlin.coroutines.CoroutineContext

class SettingViewModel :
    BaseViewModel<SettingViewContract.Event, SettingViewContract.State, SettingViewContract.Effect>() {

    override fun createInitialState(): SettingViewContract.State =
        SettingViewContract.State.default()

    override fun handleEvent(event: SettingViewContract.Event) {
        when (event) {
            SettingViewContract.Event.OnBackClick -> {
                setEffect { SettingViewContract.Effect.NavigateBack }
            }
            is SettingViewContract.Event.OnSettingOptionClick -> {
                setEffect { SettingViewContract.Effect.NavigateToSettingOption(event.settingOption) }
            }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}

sealed class SettingOption(val title: String, val subTitle: String) {
    object Categories : SettingOption("Categories", "Manage categories to choose from")
}
