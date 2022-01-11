package dev.chintansoni.expensetracker.ui.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import dev.chintansoni.expensetracker.ui.navigator.MainNavigator
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.inject
import org.koin.androidx.compose.viewModel

const val ROUTE_SIGN_IN = "SignIn"

@Composable
fun SignInView() {
    val signInViewModel by viewModel<SignInViewModel>()
    val mainNavigator: MainNavigator by inject()

    val email: String by signInViewModel.emailSF.collectAsState()
    val password: String by signInViewModel.passwordSF.collectAsState()

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

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = email,
            singleLine = true,
            onValueChange = { signInViewModel.setEmail(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Email") }
        )

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = password,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { signInViewModel.setPassword(it) },
            label = { Text("Password") }
        )

        Button(
            onClick = {
                signInViewModel.setLoginPreference()
                mainNavigator.navigate(MainRoute.SignInToHomeViewRoute)
            }, modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Sign In", modifier = Modifier.padding(4.dp))
        }

        TextButton(
            onClick = { mainNavigator.navigate(MainRoute.ForgotPasswordViewRoute) },
            modifier = Modifier
                .width(280.dp)
        ) {
            Text("Forgot password?", modifier = Modifier.padding(4.dp))
        }

        TextButton(
            onClick = { mainNavigator.navigate(MainRoute.SignUpViewRoute) }, modifier = Modifier
                .width(280.dp)
        ) {
            Text("Don't have an account? Sign Up", modifier = Modifier.padding(4.dp))
        }
    }
}