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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.chintansoni.expensetracker.R
import dev.chintansoni.expensetracker.ui.navigator.MainViewRoute
import dev.chintansoni.expensetracker.ui.navigator.navigate
import dev.chintansoni.expensetracker.ui.theme.Typography
import org.koin.androidx.compose.viewModel

const val ROUTE_PROFILE = "profile"

@Composable
fun ProfileView(navController: NavController = rememberNavController()) {

    val viewModel by viewModel<ProfileViewModel>()

    val state by viewModel.uiState.collectAsState()

    val effect by viewModel.effect.collectAsState(initial = ProfileViewContract.Effect.Nothing)
    LaunchedEffect(key1 = effect, block = {
        when (effect) {
            ProfileViewContract.Effect.NavigateToSignIn -> {
                navController.navigate(MainViewRoute.HomeToSignInViewRoute)
            }
            else -> {}
        }
    })
    val onLogout: () -> Unit = {
        viewModel.setEvent(ProfileViewContract.Event.OnLogoutClick)
    }
    ProfileContent(
        state = state,
        onLogout = onLogout
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileContent(
    state: ProfileViewContract.State = ProfileViewContract.State.default(),
    onLogout: () -> Unit = {}
) {
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
