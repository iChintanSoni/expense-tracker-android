package dev.chintansoni.expensetracker.ui.home

import dev.chintansoni.expensetracker.ui.home.analytics.AnalyticsViewModel
import dev.chintansoni.expensetracker.ui.home.profile.ProfileViewModel
import dev.chintansoni.expensetracker.ui.home.setting.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        AnalyticsViewModel(transactionDetailRepository = get())
    }
    viewModel {
        ProfileViewModel(userRepository = get())
    }
    viewModel {
        SettingViewModel()
    }
}