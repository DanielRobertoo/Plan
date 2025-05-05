package com.example.base.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun EmailTextField(
    value: String,
    emailChange: (String) -> Unit,
    emailError: Boolean,
    emailErrorFormat: String,
    label: String
) {
    OutlinedTextField(
        value = value, onValueChange = emailChange, isError = emailError,
        singleLine = true,
        supportingText = {
            Row {
                Text(if (emailError) emailErrorFormat else "")
                Spacer(Modifier.weight(1f))
                Text("${value.length}/50")
            }
        }, label = { Text(label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Email,
                contentDescription = "Icono del Email"
            )
        },
        trailingIcon = {
            if (!emailError) Icon(
                imageVector = Icons.Outlined.CheckCircle,
                contentDescription = "",
                tint = Color.Green
            ) else Icon(
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = "",
                tint = Color.Red,
                modifier = Modifier.rotate(45F)
            )
        },
        shape = RoundedCornerShape(10.0.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}