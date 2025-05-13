package com.example.validateemail.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import com.example.validateemail.usecase.ValidateEmailState

@Composable
fun ValidateEmailScreen(){

}


@Composable
fun ValidateEmailContent(state: ValidateEmailState, events: ValidateEmailEvents){
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)) {
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
            onClick = { events.onValidate() },
            modifier = Modifier
                .width(120.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xff003a), Color(0xff5100))
                    ),
                    shape = RoundedCornerShape(30.dp)
                ),
        ) {
            Text("Login")
        }

    }
}

data class ValidateEmailEvents(
    val onValidate: () -> Unit,
    val onCodeChange: (String) -> Unit
)