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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.Typography
import dev.chintansoni.expensetracker.ui.theme.emailIcon
import dev.chintansoni.expensetracker.ui.theme.passwordIcon
import dev.chintansoni.expensetracker.ui.util.TextFieldWithError
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

const val ROUTE_SIGN_IN = "SignIn"

@Composable
fun SignInView() {
    val signInViewModel by viewModel<SignInViewModel>()
    val mainNavigator: MainNavigator by inject()

    val email: String by signInViewModel.emailSF.collectAsState()
    val onEmailChange: (String) -> Unit = {
        signInViewModel.setEmail(it)
    }

    val password: String by signInViewModel.passwordSF.collectAsState()
    val onPasswordChange: (String) -> Unit = {
        signInViewModel.setPassword(it)
    }

    val onSignInClick: (email: String, password: String) -> Unit = { _email, _password ->
        signInViewModel.onSignInClick(email = _email, password = _password) {
            mainNavigator.navigate(MainRoute.SignInToHomeViewRoute)
        }
    }

    val onForgotPasswordClick: () -> Unit = {
        mainNavigator.navigate(MainRoute.ForgotPasswordViewRoute)
    }

    val onSignUpClick: () -> Unit = {
        mainNavigator.navigate(MainRoute.SignUpViewRoute)
    }

    SignInContent(
        email = email,
        onEmailChange = onEmailChange,
        password = password,
        onPasswordChange = onPasswordChange,
        onSignInClick = onSignInClick,
        onForgotPasswordClick = onForgotPasswordClick,
        onSignUpClick = onSignUpClick
    )
}

@Preview(showBackground = true)
@Composable
fun SignInContent(
    email: String = "",
    onEmailChange: (String) -> Unit = {},
    password: String = "",
    onPasswordChange: (String) -> Unit = {},
    onSignInClick: (String, String) -> Unit = { _, _ -> },
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
            value = email,
            onValueChange = onEmailChange,
            leadingIcon = emailIcon,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = "Email",
            errorText = ""
        )

        TextFieldWithError(
            modifier = Modifier.padding(8.dp),
            value = password,
            onValueChange = onPasswordChange,
            leadingIcon = passwordIcon,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = "Password",
            errorText = ""
        )

        Button(
            onClick = { onSignInClick(email, password) }, modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Sign In", modifier = Modifier.padding(4.dp))
        }

        TextButton(
            onClick = onForgotPasswordClick,
            modifier = Modifier
                .width(280.dp)
        ) {
            Text("Forgot password?", modifier = Modifier.padding(4.dp))
        }

        OutlinedButton(
            onClick = onSignUpClick, modifier = Modifier
                .width(280.dp)
        ) {
            Text("Don't have an account? Sign Up", modifier = Modifier.padding(4.dp))
        }
    }
}