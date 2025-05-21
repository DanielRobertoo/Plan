package com.example.chat.ui.base

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.DataBase.Gym
import io.github.jan.supabase.postgrest.postgrest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleExposedDropdownMenuEditar(
    cadena: String,
    contenido: String,
    Error: Boolean,
    opciones: List<String>,
    accion: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            shape = RoundedCornerShape(10.dp),
            value = contenido,
            onValueChange = { accion(it) },
            readOnly = true,
            label = { Text("$cadena") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .width(300.dp),
            isError = Error,

            )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEach { item: String ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        accion(item)
                        expanded = false
                    }
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SimpleExposedDropdownMomentoDia(
    cadena: String,
    contenido: String,
    Error: Boolean,
    accion: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = contenido,
            onValueChange = { accion(it) },
            readOnly = true,
            label = { Text("$cadena") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .width(300.dp)
                .combinedClickable(onClick = { expanded = true }, onLongClick = {}),
            isError = Error,
            placeholder = { Text("Momento del día") }

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val lista = mutableListOf<String>("Mañana", "Medio Día", "Tarde")
            lista.forEach { item: String ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        accion(item)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SimpleExposedDropdownGimnasio(
    cadena: String,
    contenido: String,
    Error: Boolean,
    accion: (String) -> Unit,
    datos: List<Gym>
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = contenido,
            onValueChange = { accion(it) },
            readOnly = true,
            label = { Text("$cadena") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .width(300.dp)
                .combinedClickable(onClick = { expanded = true }, onLongClick = {}),
            isError = Error,
            placeholder = { Text("Momento del día") }

        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            datos.forEach { item: Gym ->
                DropdownMenuItem(
                    text = { Text(item.name) },
                    onClick = {
                        accion(item.name)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleExposedDropdownMinutos(
    cadena: String,
    contenido: String,
    Error: Boolean,
    accion: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            value = contenido,
            onValueChange = { accion(it) },
            readOnly = true,
            label = { Text("$cadena") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .width(170.dp)
                .padding(5.dp),
            isError = Error,

            )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val lista = mutableListOf<String>()
            for (i in 0..59) {
                lista.add(i.toString())
            }
            lista.forEach { item: String ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        accion(item)
                        expanded = false
                    }
                )
            }
        }
    }
}