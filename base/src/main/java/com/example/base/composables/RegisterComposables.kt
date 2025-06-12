package com.example.base.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

@Composable
fun UserNameField(
    value: String,
    label: String,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    errorFormat: String
) {
    TextField(value = value,
        singleLine = true,
        isError = isError,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.0.dp),
        supportingText = {
            Row {
                Text(
                    text = if (isError) errorFormat else "",
                    Modifier.clearAndSetSemantics { value })
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(label) })
}

@Composable
fun SurnameField(
    value: String,
    label: String,
    isError: Boolean,
    errorFormat: String,
    onValueChange: (String) -> Unit
) {
    TextField(value = value,
        singleLine = true,
        isError = isError,
        onValueChange = onValueChange,
        shape = RoundedCornerShape(10.0.dp),
        supportingText = {
            Row {
                Text(
                    text = if (isError) errorFormat else "",
                    Modifier.clearAndSetSemantics { value })
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(label) })
}

@Composable
fun NameField(
    value: String,
    label: String,
    isError: Boolean,
    errorFormat: String,
    onNameChange: (String) -> Unit
) {
    TextField(value = value,
        singleLine = true,
        isError = isError,
        onValueChange = onNameChange,
        shape = RoundedCornerShape(10.0.dp),
        supportingText = {
            Row {
                Text(
                    text = if (isError) errorFormat else "",
                    Modifier.clearAndSetSemantics { value })
                Spacer(Modifier.weight(1f))
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        label = { Text(label) })
}

@Composable
fun BirthdayField(
    value: Date?,
    isDatePickerVisible: MutableState<Boolean>,
    isError: Boolean,
    formatError: String,
    onDateSelected: (Date?) -> Unit
) {
    val formattedDate = value?.let {
        it.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().format(
            DateTimeFormatter.ofPattern("dd/MM/yyyy")
        )
    } ?: ""
    TextField(
        value = formattedDate,
        onValueChange = { isDatePickerVisible.value = true },
        label = { Text("Fecha de nacimiento") },
        enabled = false,
        modifier = Modifier.clickable { isDatePickerVisible.value = true },
        shape = RoundedCornerShape(20.0.dp),
        supportingText = {
            Row {
                Text(
                    text = if (isError) formatError else "",
                    Modifier.clearAndSetSemantics { value })
            }
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
    if (isDatePickerVisible.value) {
        myDatPickDia(
            onDismiss = { isDatePickerVisible.value = false },
            onDateSelected = onDateSelected
        )
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun myDatPickDia(onDismiss: () -> Unit, onDateSelected: (Date?) -> Unit) {
    val datepickerstate = rememberDatePickerState()
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datepickerstate.selectedDateMillis?.let { millis ->
                    Date.from(Instant.ofEpochMilli(millis))
                });
                onDismiss()
            }) { Text("OK") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }) {
        DatePicker(
            state = datepickerstate,
            dateFormatter = DatePickerDefaults.dateFormatter()
        )
    }
}
@Composable
fun ButtonRegister(onRegisterClick: () -> Unit) {
    TextButton(modifier = Modifier
        .width(120.dp)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFF00FFB2), Color(0xFF00A7FF))
            ),
            shape = RoundedCornerShape(30.dp)
        ), onClick = { onRegisterClick() }) {
        Text("Register", color = Color.Black)

    }

}