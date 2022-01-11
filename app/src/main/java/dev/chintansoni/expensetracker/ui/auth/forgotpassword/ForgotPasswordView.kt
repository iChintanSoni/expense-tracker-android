package dev.chintansoni.expensetracker.ui.auth.forgotpassword

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.KeyboardType
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

    val sendLinkStatusMS: MutableState<SendLinkStatus> = remember {
        mutableStateOf(SendLinkStatus.Default)
    }
    val sendLinkStatus: SendLinkStatus by sendLinkStatusMS

//    lifecycleCoroutineScope.launchWhenStarted {
//        forgotPasswordViewModel.sendLinkStatusMSF.collect {
//            sendLinkStatusMS.value = it
//        }
//    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .background(color = Color.LightGray)
                .padding(16.dp)
                .layoutId("column"),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Forgot Password",
                style = Typography.h5,
            )

            OutlinedTextField(
                enabled = sendLinkStatus != SendLinkStatus.InProgress,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                value = email,
                singleLine = true,
                onValueChange = { forgotPasswordViewModel.setEmail(it) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text("Email") }
            )

            Button(
                onClick = {
                    forgotPasswordViewModel.sendLink()
                },
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                enabled = sendLinkStatus != SendLinkStatus.InProgress
            ) {
                Text("Send Link", modifier = Modifier.padding(4.dp))
            }

            TextButton(
                onClick = { mainNavigator.navigate(MainRoute.GoBackViewRoute) },
                enabled = sendLinkStatus != SendLinkStatus.InProgress
            ) {
                Text("Go back", modifier = Modifier.padding(4.dp))
            }
        }

    }

    if (sendLinkStatus == SendLinkStatus.Success) {
        Snackbar(
            action = {
                TextButton(
                    onClick = { mainNavigator.navigate(MainRoute.GoBackViewRoute) },
                ) {
                    Text("Okay")
                }
            },
            modifier = Modifier
                .layoutId("snackbar")
                .padding(8.dp)
                .fillMaxWidth()
        ) { Text(text = "Sent successfully.") }
    }
}