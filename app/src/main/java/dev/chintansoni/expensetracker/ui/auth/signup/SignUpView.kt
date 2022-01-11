package dev.chintansoni.expensetracker.ui.auth.signup

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

const val ROUTE_SIGN_UP = "SignUp"

@Composable
fun SignUpView() {
    val signUpViewModel: SignUpViewModel by viewModel()
    val mainNavigator: MainNavigator by inject()

    val firstName: String by signUpViewModel.firstNameSF.collectAsState()
    val lastName: String by signUpViewModel.lastNameSF.collectAsState()
    val email: String by signUpViewModel.emailSF.collectAsState()
    val mobile: String by signUpViewModel.mobileSF.collectAsState()

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

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = firstName,
            singleLine = true,
            onValueChange = { signUpViewModel.setFirstName(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text("First Name") }
        )

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = lastName,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = { signUpViewModel.setLastName(it) },
            label = { Text("Last Name") }
        )

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = email,
            singleLine = true,
            onValueChange = { signUpViewModel.setEmail(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            label = { Text("Email") }
        )

        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = mobile,
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { signUpViewModel.setMobile(it) },
            label = { Text("Mobile") }
        )

        Button(
            onClick = { /*TODO*/ }, modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Sign Up", modifier = Modifier.padding(4.dp))
        }

        Button(
            onClick = { mainNavigator.navigate(MainRoute.GoBackViewRoute) }, modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Back", modifier = Modifier.padding(4.dp))
        }
    }
}