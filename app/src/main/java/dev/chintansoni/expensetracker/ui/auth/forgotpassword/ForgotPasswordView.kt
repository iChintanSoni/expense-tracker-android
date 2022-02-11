package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

const val ROUTE_FORGOT_PASSWORD = "ForgotPassword"

@Composable
fun ForgotPasswordScreen() {

    val mainNavigator: MainNavigator by inject()
    val forgotPasswordViewModel: ForgotPasswordViewModel by viewModel()

    val email: String by forgotPasswordViewModel.emailSF.collectAsState()
    val onEmailChange: (String) -> Unit = {}

    val sendLinkStatus: SendLinkStatus by forgotPasswordViewModel.sendLinkStatusSF.collectAsState()

    val onSendLinkClick: () -> Unit = {
        forgotPasswordViewModel.sendLink()
    }

    val onBackClick: () -> Unit = {
        mainNavigator.navigate(MainRoute.GoBackViewRoute)
    }

    val onTryAgainClick: () -> Unit = {
        forgotPasswordViewModel.resetSendLinkStatus()
    }

    ForgotPasswordContent(
        email = email,
        onEmailChange = onEmailChange,
        onSendLinkClick = onSendLinkClick,
        onBackClick = onBackClick,
        onTryAgainClick = onTryAgainClick,
        sendLinkStatus = sendLinkStatus
    )
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordContent(
    email: String = "",
    onEmailChange: (String) -> Unit = {},
    onSendLinkClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTryAgainClick: () -> Unit = {},
    sendLinkStatus: SendLinkStatus = SendLinkStatus.Default
) {
    Crossfade(targetState = sendLinkStatus) {
        when (it) {
            is SendLinkStatus.Success -> Success(onBackClick)
            is SendLinkStatus.Failure -> Failure(onTryAgainClick)
            else -> ForgotPasswordForm(
                email = email,
                onEmailChange = onEmailChange,
                onSendLinkClick = onSendLinkClick,
                onBackClick = onBackClick,
                sendLinkStatus = sendLinkStatus
            )
        }
    }
}

@Composable
fun ForgotPasswordForm(
    email: String = "",
    onEmailChange: (String) -> Unit = {},
    onSendLinkClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    sendLinkStatus: SendLinkStatus = SendLinkStatus.Default
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

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp),
            value = email,
            enabled = sendLinkStatus != SendLinkStatus.InProgress,
            singleLine = true,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Email") }
        )

        Button(
            onClick = onSendLinkClick,
            enabled = sendLinkStatus != SendLinkStatus.InProgress,
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text(
                if (sendLinkStatus != SendLinkStatus.InProgress) "Send Link" else "Sending Link",
                modifier = Modifier.padding(4.dp)
            )
        }

        TextButton(
            onClick = onBackClick,
            enabled = sendLinkStatus != SendLinkStatus.InProgress,
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Go back", modifier = Modifier.padding(4.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Success(onBackClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Password reset link has been sent successfully",
            style = Typography.h5,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        TextButton(onClick = onBackClick) {
            Text(text = "Go to Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Failure(onTryAgainClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .width(280.dp)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Error sending email",
            style = Typography.h5,
        )
        TextButton(onClick = onTryAgainClick) {
            Text(text = "Try Again")
        }
    }
}