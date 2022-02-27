package dev.chintansoni.expensetracker.ui.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
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
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.Typography
import dev.chintansoni.expensetracker.ui.theme.emailIcon
import dev.chintansoni.expensetracker.ui.theme.passwordIcon
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.viewModel

const val ROUTE_SIGN_IN = "SignIn"

@Composable
fun SignInView(navController: NavController = rememberNavController()) {

    val signInViewModel by viewModel<SignInViewModel>()

    val state by signInViewModel.uiState.collectAsState()

    val effect by signInViewModel.effect.collectAsState(initial = SignInContract.Effect.Nothing)

    LaunchedEffect(key1 = effect) {
        when (effect) {
            SignInContract.Effect.NavigateToHome -> {
                navController.navigate(MainRoute.SignInToHomeViewRoute)
            }
            SignInContract.Effect.NavigateToForgotPassword -> {
                navController.navigate(MainRoute.ForgotPasswordViewRoute)
            }
            SignInContract.Effect.NavigateToSignUp -> {
                navController.navigate(MainRoute.SignUpViewRoute)
            }
            else -> {}
        }
    }

    val onEmailChange: (String) -> Unit = {
        signInViewModel.setEvent(SignInContract.Event.OnEmailChange(it))
    }
    val onPasswordChange: (String) -> Unit = {
        signInViewModel.setEvent(SignInContract.Event.OnPasswordChange(it))
    }

    val onSignInClick: () -> Unit = {
        signInViewModel.setEvent(SignInContract.Event.OnSignInClick)
    }

    val onForgotPasswordClick: () -> Unit = {
        navController.navigate(MainRoute.ForgotPasswordViewRoute)
    }

    val onSignUpClick: () -> Unit = {
        navController.navigate(MainRoute.SignUpViewRoute)
    }

    SignInContent(
        state = state,
        onEmailChange = onEmailChange,
        onPasswordChange = onPasswordChange,
        onSignInClick = onSignInClick,
        onForgotPasswordClick = onForgotPasswordClick,
        onSignUpClick = onSignUpClick
    )
}

@Preview(showBackground = true)
@Composable
fun SignInContent(
    state: SignInContract.State = SignInContract.State.default(),
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onSignInClick: () -> Unit = { },
    onForgotPasswordClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Sign In",
            style = Typography.h5,
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = state.email,
            enabled = !state.isLoading,
            onValueChange = onEmailChange,
            leadingIcon = emailIcon,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = "Email",
            errorText = state.emailError
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = state.password,
            enabled = !state.isLoading,
            onValueChange = onPasswordChange,
            leadingIcon = passwordIcon,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = "Password",
            errorText = state.passwordError
        )

        Button(
            onClick = { onSignInClick() },
            enabled = !state.isLoading,
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Sign In", modifier = Modifier.padding(4.dp))
        }

        TextButton(
            onClick = onForgotPasswordClick,
            enabled = !state.isLoading,
            modifier = Modifier
                .width(280.dp)
        ) {
            Text("Forgot password?", modifier = Modifier.padding(4.dp))
        }

        OutlinedButton(
            onClick = onSignUpClick,
            enabled = !state.isLoading,
            modifier = Modifier
                .width(280.dp)
        ) {
            Text("Don't have an account? Sign Up", modifier = Modifier.padding(4.dp))
        }
    }
}