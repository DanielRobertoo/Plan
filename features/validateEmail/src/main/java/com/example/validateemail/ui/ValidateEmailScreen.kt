package com.example.validateemail.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.example.chat.ui.base.AlertDialogYesNo
import com.example.validateemail.usecase.ValidateEmailState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ValidateEmailScreen(viewModel: ValidateEmailViewModel, goLogin: () -> Unit, goBack: () -> Unit, email: String, password: String, userId: String){
    LaunchedEffect(Unit) {
        viewModel.assignEmail(email, password, userId)
    }
    Log.d("Validate", "creado ${viewModel.state}")
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {goBack()},
                        content = { Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "") }
                    )
                }
            )
        }
    ){
        Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.TopCenter){
            ValidateEmailViewModelScreen(
                state = viewModel.state,
                events = ValidateEmailEvents(
                    reset = viewModel::reset,
                    onValidate = { viewModel.validateEmail(goLogin) } ,
                    onCodeChange = viewModel::onCodeChange,
                    goBack = goBack,
                    goLogin = goLogin
                )
            )
        }

    }

}


@Composable
fun ValidateEmailViewModelScreen(state: ValidateEmailState, events: ValidateEmailEvents){
    when{
        state.fail -> AlertDialogYesNo(title = "ERROR CODIGO", message = "El codigo es invalido, quieres volver al registro?", onDismiss = { events.reset() }, onAccept = { events.goBack() })
        else -> ValidateEmailContent(
            state = state, events = events, goLogin = events.goLogin
        )
    }
}


@Composable
fun ValidateEmailContent(state: ValidateEmailState, events: ValidateEmailEvents, goLogin: () -> Unit){
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            value = state.code,
            singleLine = true,
            onValueChange = { events.onCodeChange(it) },
            shape = RoundedCornerShape(10.0.dp),

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        )
        TextButton(
            onClick = { events.onValidate(goLogin) },
            modifier = Modifier
                .width(120.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xff003a), Color(0xff5100))
                    ),
                    shape = RoundedCornerShape(30.dp)
                ),
        ) {
            Text("Validate")
        }

    }
}

data class ValidateEmailEvents(
    val onValidate: (() -> Unit) -> Unit,
    val onCodeChange: (String) -> Unit,
    val goLogin: () -> Unit,
    val goBack: () -> Unit,
    val reset: () -> Unit
)