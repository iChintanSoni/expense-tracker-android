package dev.chintansoni.expensetracker.ui.home.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.navigator.MainRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.viewModel

const val ROUTE_PROFILE = "profile"

@Composable
fun ProfileView(navController: NavController = rememberNavController()) {

    val profileViewModel by viewModel<ProfileViewModel>()

    val onLogout: () -> Unit = {
        profileViewModel.logout {
            navController.navigate(MainRoute.HomeToSignInViewRoute)
        }
    }
    ProfileContent(onLogout)
}

@Preview(showBackground = true)
@Composable
fun ProfileContent(onLogout: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.surface)
            .wrapContentSize(Alignment.Center),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.ic_account_circle),
            contentDescription = "Account picture",
            modifier = Modifier
                .width(96.dp)
                .height(96.dp)
        )

        Text(text = "Chintan Soni", style = Typography.h6)

        TextButton(
            onClick = onLogout,
            modifier = Modifier
                .padding(12.dp)
                .width(280.dp)
        ) {
            Text("Logout", modifier = Modifier.padding(4.dp))
        }
    }
}