package dev.chintansoni.expensetracker.ui.auth

import dev.chintansoni.expensetracker.ui.auth.forgotpassword.ForgotPasswordViewModel
import dev.chintansoni.expensetracker.ui.auth.signin.SignInViewModel
import dev.chintansoni.expensetracker.ui.auth.signup.SignUpViewModel
import dev.chintansoni.expensetracker.ui.auth.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    viewModel {
        SplashViewModel(get())
    }
    viewModel {
        SignInViewModel(
            userRepository = get(),
            categoryRepository = get()
        )
    }
    viewModel {
        SignUpViewModel()
    }
    viewModel {
        ForgotPasswordViewModel()
    }
}