package dev.chintansoni.expensetracker.ui.auth

import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ForgotPasswordViewModel
import dev.chintansoni.expensetracker.ui.auth.signin.SignInViewModel
import dev.chintansoni.expensetracker.ui.auth.signup.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel {
        SignInViewModel(get())
    }
    viewModel {
        SignUpViewModel()
    }
    viewModel {
        ForgotPasswordViewModel()
    }
}