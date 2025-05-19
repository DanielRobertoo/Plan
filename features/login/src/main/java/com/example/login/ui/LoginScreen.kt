package com.example.login.ui.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.example.base.composables.EmailTextField
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.base.composables.PasswordField
import com.example.base.composables.SpaceMedio
import com.example.chat.ui.base.AlertDialogOK


/**
 * Login screen:
 *
 */
data class LoginEvents(
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val onClickLogin: () -> Unit = {},
    val onSignupClick: () -> Unit = {}
)


@Composable
fun LoginScreen(
    email: String,
    password: String,
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    goToRegister: () -> Unit,
    goToListAccount: () -> Unit,
    goback: () -> Unit
) {
    Log.d("Login", "creado ${viewModel.state}")
    val eventos = LoginEvents(
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onClickLogin = { viewModel.onLoginClick(goToListAccount)},
        onSignupClick = { goToRegister() })
    LoginScreenViewModel(modifier, viewModel.state, eventos, goToListAccount, goback)
}

@Composable
fun LoginScreenViewModel(
    modifier: Modifier = Modifier,
    state: AccountLoginState,
    events: LoginEvents,
    goToListAccount: () -> Unit,
    goback: () -> Unit
) {
    when {
        state.isLoading -> LoadingUi()
        state.isOffline -> NoDataScreen()
        state.isErrorAccount -> AlertDialogOK(
            title = "Error",
            message = "Error al iniciar sesión",
            onDismiss = {
                state.isErrorAccount = false
                goback()
            }
        )
        else -> {
            LoginFormContent(
                modifier = modifier,
                state = state,
                events = events,
                goList= goToListAccount
            )
        }
    }
}

@Composable
fun LoginFormContent(
    modifier: Modifier = Modifier,
    state: AccountLoginState,
    events: LoginEvents,
    goList: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
            )
            SpaceMedio()
            EmailTextField(
                value = state.email,
                label = "Email",
                emailError = state.isEmailError,
                emailChange = events.onEmailChange,
                emailErrorFormat = state.emailErrorFormat
            )
            PasswordField(
                modifier = Modifier,
                value = state.password,
                label = "Password",
                isError = state.isPasswordError,
                onValueChange = events.onPasswordChange, errorFormat = state.passwordErrorFormat
            )
            TextButton(
                onClick = { events.onClickLogin() },
                modifier =
                Modifier
                    .width(120.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF00FFB2), Color(0xFF00A7FF))
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ),
            ) {
                Text("Login")
            }
            SpaceMedio()
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Si no tienes una cuenta: ", style = TextStyle(color = Color.Black))
                Text(
                    modifier = Modifier.clickable(
                        onClick = { events.onSignupClick() },
                        enabled = true
                    ),
                    text = "Register",
                    style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)
                )
            }


        }
    }


}

@Preview(showBackground = true)
@Composable
fun LoginFormContentPreviewOnly() {
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Login",
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally),
            )

            SpaceMedio()

            // Email placeholder
            EmailTextField(
                value = "example@email.com",
                label = "Email",
                emailError = false,
                emailChange = {},
                emailErrorFormat = "false"
            )

            // Password placeholder
            PasswordField(
                modifier = Modifier,
                value = "password123",
                label = "Password",
                isError = false,
                onValueChange = {},
                errorFormat = "false"
            )

            TextButton(
                onClick = { /* no-op */ },
                modifier = Modifier
                    .width(120.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFF00FFB2), Color(0xFF00A7FF))
                        ),
                        shape = RoundedCornerShape(30.dp)
                    ),
            ) {
                Text("Login")
            }

            SpaceMedio()

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text("Si no tienes una cuenta: ", style = TextStyle(color = Color.Black))
                Text(
                    modifier = Modifier.clickable(
                        onClick = { /* no-op */ },
                        enabled = true
                    ),
                    text = "Register",
                    style = TextStyle(color = Color.Blue, textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}



