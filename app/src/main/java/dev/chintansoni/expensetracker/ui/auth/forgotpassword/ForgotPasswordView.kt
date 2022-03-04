package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.navigator.navigateBack
import dev.chintansoni.expensetracker.ui.theme.Typography
import dev.chintansoni.expensetracker.ui.util.SuccessFailureView
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel

const val ROUTE_FORGOT_PASSWORD = "ForgotPassword"

@Composable
fun ForgotPasswordScreen(navController: NavController = rememberNavController()) {

    val viewModel: ForgotPasswordViewModel by viewModel()
    val state by viewModel.uiState.collectAsState()
    val effect by viewModel.effect.collectAsState(initial = ForgotPasswordContract.Effect.Idle)

    LaunchedEffect(key1 = effect) {
        when (effect) {
            is ForgotPasswordContract.Effect.NavigateBack -> {
                navController.navigateBack()
            }
            else -> {}
        }
    }

    val onEmailChange: (String) -> Unit = {
        viewModel.setEvent(ForgotPasswordContract.Event.OnEmailChange(it))
    }

    val onSendLinkClick: () -> Unit = {
        viewModel.setEvent(ForgotPasswordContract.Event.OnSendLinkClick)
    }

    val onBackClick: () -> Unit = {
        viewModel.setEvent(ForgotPasswordContract.Event.OnBackClick)
    }

    val onTryAgainClick: () -> Unit = {
        viewModel.setEvent(ForgotPasswordContract.Event.OnTryAgainClick)
    }

    ForgotPasswordContent(
        state = state,
        onEmailChange = onEmailChange,
        onSendLinkClick = onSendLinkClick,
        onBackClick = onBackClick,
        onTryAgainClick = onTryAgainClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun ForgotPasswordContent(
    state: ForgotPasswordContract.State = ForgotPasswordContract.State.default(),
    onEmailChange: (String) -> Unit = {},
    onSendLinkClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTryAgainClick: () -> Unit = {}
) {
    Crossfade(targetState = state.forgotPasswordApiState) {
        when (it) {
            is ForgotPasswordContract.ForgotPasswordApiState.Success -> SuccessFailureView(
                title = "Success",
                description = "Password reset has been sent to your email",
                actionLabel = "Back to login",
                onActionClick = onBackClick
            )
            is ForgotPasswordContract.ForgotPasswordApiState.Failure -> SuccessFailureView(
                title = "Failure",
                description = "Email not registered, please try again",
                actionLabel = "Try again",
                onActionClick = onTryAgainClick
            )
            else -> FormView(
                state = state,
                onEmailChange = onEmailChange,
                onSendLinkClick = onSendLinkClick,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
private fun FormView(
    state: ForgotPasswordContract.State = ForgotPasswordContract.State.default(),
    onEmailChange: (String) -> Unit = {},
    onSendLinkClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Forgot Password",
            style = Typography.h5,
        )

        TextFieldWithError(
            modifier = Modifier
                .padding(8.dp),
            value = state.email,
            errorText = state.emailError,
            enabled = state.forgotPasswordApiState.isNotInProgress(),
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = "Email"
        )

        Button(
            onClick = onSendLinkClick,
            enabled = state.forgotPasswordApiState.isNotInProgress(),
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text(
                text = if (state.forgotPasswordApiState.isNotInProgress()) "Send Link" else "Sending Link",
                modifier = Modifier.padding(4.dp)
            )
        }

        TextButton(
            onClick = onBackClick,
            enabled = state.forgotPasswordApiState.isNotInProgress(),
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text(
                text = "Go back",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}