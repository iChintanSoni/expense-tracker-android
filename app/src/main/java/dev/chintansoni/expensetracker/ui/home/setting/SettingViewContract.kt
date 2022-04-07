package dev.chintansoni.expensetracker.ui.home.setting

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class SettingViewContract {

    sealed class Event : UiEvent {
        object OnBackClick : Event()
        data class OnSettingOptionClick(val settingOption: SettingOption) : Event()
    }

    data class State(val settingOptions: List<SettingOption>) : UiState {
        companion object {
            fun default() = State(
                listOf(SettingOption.Categories)
            )
        }
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
        data class NavigateToSettingOption(val settingOption: SettingOption) : Effect()
        object NavigateBack : Effect()
    }
}