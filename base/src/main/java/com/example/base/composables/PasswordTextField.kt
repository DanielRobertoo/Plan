package com.example.base.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordField(
    modifier: Modifier,
    value: String,
    label: String,
    errorFormat: String,
    isError: Boolean,
    onValueChange: (String) -> Unit
) {
    TextField(modifier = Modifier.padding(10.dp),
        value = value,
        singleLine = true,
        isError = isError,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.0.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(label) },
        visualTransformation = PasswordVisualTransformation()
    )
}