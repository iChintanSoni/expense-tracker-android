package dev.chintansoni.expensetracker.ui.auth.signup

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.ui.navigator.navigateBack
import dev.chintansoni.expensetracker.ui.theme.Typography
import dev.chintansoni.expensetracker.ui.util.SuccessFailureView
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel

const val ROUTE_SIGN_UP = "SignUp"

@Composable
fun SignUpView(navController: NavController = rememberNavController()) {

    val viewModel: SignUpViewModel by viewModel()
    val uiState: SignUpContract.State by viewModel.uiState.collectAsState()
    val effect: SignUpContract.Effect by viewModel.effect.collectAsState(initial = SignUpContract.Effect.Nothing)

    LaunchedEffect(key1 = effect) {
        when (effect) {
            SignUpContract.Effect.NavigateBack, SignUpContract.Effect.NavigateToSignIn ->
                navController.navigateBack()
            else -> {}
        }
    }

    val onFirstNameChange: (String) -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnFirstNameChange(it))
    }
    val onLastNameChange: (String) -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnLastNameChange(it))
    }
    val onEmailChange: (String) -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnEmailChange(it))
    }
    val onPasswordChange: (String) -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnPasswordChange(it))
    }
    val onSignUpClick: () -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnSignUpClick)
    }
    val onBackClick: () -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnBackClick)
    }
    val onTryAgainClick: () -> Unit = {
        viewModel.setEvent(SignUpContract.Event.OnTryAgainClick)
    }

    SignUpContent(
        uiState = uiState,
        onFirstNameChange = onFirstNameChange,
        onLastNameChange = onLastNameChange,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onSignUpClick = onSignUpClick,
        onBackClick = onBackClick,
        onTryAgainClick = onTryAgainClick
    )
}

@Preview(showBackground = true)
@Composable
fun SignUpContent(
    uiState: SignUpContract.State = SignUpContract.State.default(),
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onBackClick: () -> Unit = {},
    onTryAgainClick: () -> Unit = {}
) {
    Crossfade(targetState = uiState.signUpApiState) {
        when (it) {
            SignUpContract.SignUpApiState.Idle, SignUpContract.SignUpApiState.InProgress -> {
                FormView(
                    uiState = uiState,
                    onFirstNameChange = onFirstNameChange,
                    onLastNameChange = onLastNameChange,
                    onEmailChange = onEmailChange,
                    onPasswordChange = onPasswordChange,
                    onSignUpClick = onSignUpClick,
                    onBackClick = onBackClick
                )
            }
            is SignUpContract.SignUpApiState.Success -> {
                SuccessFailureView(
                    title = "Success",
                    description = it.successMessage,
                    actionLabel = "Go to login",
                    onActionClick = onBackClick
                )
            }
            is SignUpContract.SignUpApiState.Failure -> {
                SuccessFailureView(
                    title = "Failure",
                    description = it.errorMessage,
                    actionLabel = "Try again",
                    onActionClick = onTryAgainClick
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FormView(
    uiState: SignUpContract.State = SignUpContract.State.default(),
    onFirstNameChange: (String) -> Unit = {},
    onLastNameChange: (String) -> Unit = {},
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign Up",
            style = Typography.h5,
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = uiState.firstName,
            singleLine = true,
            onValueChange = onFirstNameChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = "First Name",
            errorText = uiState.firstNameError,
            enabled = uiState.signUpApiState.isNotInProgress()
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = uiState.lastName,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = onLastNameChange,
            label = "Last Name",
            errorText = uiState.lastNameError,
            enabled = uiState.signUpApiState.isNotInProgress()
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = uiState.email,
            singleLine = true,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = "Email",
            errorText = uiState.emailError,
            enabled = uiState.signUpApiState.isNotInProgress()
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = uiState.password,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = onPasswordChange,
            label = "Password",
            errorText = uiState.passwordError,
            enabled = uiState.signUpApiState.isNotInProgress()
        )

        Button(
            onClick = onSignUpClick,
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp),
            enabled = uiState.signUpApiState.isNotInProgress()
        ) {
            Text(
                text = "Sign Up",
                modifier = Modifier.padding(4.dp)
            )
        }

        TextButton(
            onClick = onBackClick,
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp),
            enabled = uiState.signUpApiState.isNotInProgress()
        ) {
            Text(
                text = "Back",
                modifier = Modifier.padding(4.dp)
            )
        }
    }
}