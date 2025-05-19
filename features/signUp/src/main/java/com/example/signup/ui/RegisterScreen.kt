package com.example.login.ui.register

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.base.composables.BirthdayField
import com.example.base.composables.ButtonRegister
import com.example.base.composables.EmailTextField
import com.example.base.composables.LoadingUi
import com.example.base.composables.NameField
import com.example.base.composables.PasswordField
import com.example.base.composables.SpaceBajo
import com.example.base.composables.SurnameField
import com.example.base.composables.UserNameField
import com.example.chat.ui.base.AlertDialogOK
import java.time.LocalDate
import java.util.Date


data class RegisterEvents(
    val onUserNameChange: (String) -> Unit,
    val onNameChange: (String) -> Unit,
    val onEmailChange: (String) -> Unit,
    val onSurnameChange: (String) -> Unit,
    val onPasswordChange: (String) -> Unit,
    val onBirthdayChange: (String) -> Unit,
    val onClickRegister: () -> Unit ={}
){

}

@Composable
fun RegisterScreen(modifier: Modifier, viewModel: RegisterViewModel, goToLogin: () -> Unit, goValidate: (String,String) -> Unit) {
    Log.d("Register", "creado ${viewModel.state}")
    val eventos = RegisterEvents(
        onUserNameChange =  viewModel::onUserNameChange,
        onEmailChange = viewModel::onEmailChange,
        onNameChange = viewModel::onNameChange,
        onSurnameChange = viewModel::onSurnameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onBirthdayChange = viewModel::onBirthdayChange,
        onClickRegister = viewModel::onRegisterClick)
    RegisterScreenState(modifier =modifier,
        state = viewModel.state,
        events = eventos,
        goToLogin = goToLogin,
        goToValidate = goValidate,
        )

}

@Composable
fun RegisterScreenState(modifier: Modifier, state: AccountRegisterState, events: RegisterEvents, goToLogin: ()->Unit, goToValidate: (String,String) -> Unit){
    when {
        state.isLoading -> LoadingUi()
        state.isEmptyFields -> AlertDialogOK(message = state.isEmpty ?: "Campos vacios", title = "Error", onDismiss = {})
        state.success -> LaunchedEffect(state.success) { goToValidate(state.email, state.password) }
        state.emailExist -> AlertDialogOK(message = "Cuenta ya existente", title = "Error", onDismiss = {})
        else -> RegisterContent(modifier, state, events)
    }
}

@Composable
fun RegisterContent(modifier: Modifier, state: AccountRegisterState, events: RegisterEvents) {

    Box(modifier = Modifier
        .fillMaxSize()
        /*.background(
        brush = Brush.verticalGradient(
            listOf(
                Color(0xFFFFA726),
                Color(0xFF42A5F5)
            )
        )
    ),*/, contentAlignment = Alignment.Center){
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier){
            Text(text = "Register", modifier = Modifier.align(Alignment.CenterHorizontally), style = TextStyle(fontSize = 30.0.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center))
            SpaceBajo()
            UserNameField(value = state.username, isError = state.isUserError,errorFormat = state.userErrorFormat, label = "User Name", onValueChange = events.onUserNameChange)
            NameField(value = state.name, onNameChange = events.onNameChange, isError = state.isNameError, errorFormat = state.nameErrorFormat, label = "Nombre")
            SurnameField(value = state.surname, onValueChange = events.onSurnameChange, isError = state.isSurnameError, errorFormat = state.surnameErrorFormat, label = "Apellidos")
            //BirthdayField(value = state.birthday, isDatePickerVisible = state.isDatePickerVisible, isError = state.isBirthdayError, formatError = state.dateErrorFormat, onDateSelected = events.onBirthdayChange)
            EmailTextField(value = state.email, label = "Email", emailError = state.isEmailError, emailChange = events.onEmailChange, emailErrorFormat = state.emailErrorFormat)
            PasswordField(modifier=modifier.testTag("passwordField"),value = state.password, label = "Password", isError = state.isPasswordError, errorFormat = state.passwordErrorFormat, onValueChange = events.onPasswordChange)
            ButtonRegister(onRegisterClick = events.onClickRegister)



        }
    }
}


