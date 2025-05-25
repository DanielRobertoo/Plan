package com.example.chat.ui.CreatePublicacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.airport.ui.base.composables.TopAppBar
import com.example.chat.ui.base.AlertDialogOK
import com.example.chat.ui.base.DatePickerFieldToModalonChange
import com.example.chat.ui.base.SimpleExposedDropdownGimnasio
import com.example.chat.ui.base.SimpleExposedDropdownMenuEditar
import com.example.chat.ui.base.SimpleExposedDropdownMomentoDia
import com.example.createpost.usecase.CreatePublicationState
import com.example.domain.model.DataBase.Gym
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.reflect.KSuspendFunction0


@Composable
fun CreatePublicationScreen(
    goBack: () -> Unit,
    viewModel: CreatePublicationViewModel,
    modifier: Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.loadGym()
    }
    when {
        viewModel.state.errorFields -> AlertDialogOK(
            title = "ERROR",
            message = "Campos inválidos",
            onDismiss = { viewModel.resetError() })

        else -> CreatePulicationContent(viewModel.state, events = CreationPublicationEvents(
            onDescriptionChange = viewModel::onDescriptionChange,
            onHourBeginChange = viewModel::onTimeSetChange,
            onDateChange = viewModel::onDateChange,
            onTitleChange = viewModel::onTiTleChange,
            onMomentDayChange = viewModel::onMomentDayChange,
            onGymChange = viewModel::onGymChange,
            goAdd = { viewModel.onCreatePost(goBack) }
        ),
            goBack = goBack,
            gymList = viewModel.gym
        )
    }

}

data class CreationPublicationEvents(
    val onTitleChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,
    val onDateChange: (String) -> Unit,
    val onGymChange: (String) -> Unit,
    val onHourBeginChange: (String) -> Unit,
    val onMomentDayChange: (String) -> Unit,
    val goAdd: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePulicationContent(
    state: CreatePublicationState,
    events: CreationPublicationEvents,
    goBack: () -> Unit,
    gymList: List<Gym>
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { events.goAdd() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Añadir Avería"
                )
            }

        },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Crear Post") },
                navigationIcon = { IconButton(onClick = { goBack() }, content = { Icon( imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")}) },
            )
        }
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
                .padding(it)
        ) {

            SimpleExposedDropdownMenuEditar(
                Error = false,
                opciones = listOf(
                    "Pecho y tríceps",
                    "Espalda y bíceps",
                    "Piernas (cuádriceps, femorales, glúteos)",
                    "Hombros y trapecios",
                    "Core (abdominales y zona lumbar)",
                    "Cardio (cinta, elíptica, bici)",
                    "Full body (entrenamiento de cuerpo completo)",
                    "HIIT (entrenamiento por intervalos de alta intensidad)",
                    "Día de movilidad y estiramientos",
                    "Entrenamiento funcional o cross-training"
                ),
                cadena = "Rutina",
                accion = events.onTitleChange,
                contenido = state.title
            )
            Spacer(modifier = Modifier.padding(5.dp))

            OutlinedTextField(
                value = state.description,
                onValueChange = { events.onDescriptionChange(it) },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .width(300.dp)
                    .height(150.dp),
                placeholder = { Text("Descripción") }
            )
            SimpleExposedDropdownGimnasio(
                Error = false,
                accion = { events.onGymChange(it) },
                cadena ="Gimnasio",
                contenido = state.gym,
                datos = gymList
            )
            DatePickerFieldToModalonChange(
                label = "",
                Error = false,
                accion = { events.onDateChange },
                string = "fecha",
            )
            Row {
                SimpleExposedDropdownMomentoDia(
                    Error = false,
                    accion = { events.onMomentDayChange(it) },
                    contenido = state.momentDay,
                    cadena = "Momento del dia"
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreatePulicationContentPreview() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {
        OutlinedTextField(
            value = "Título",
            onValueChange = {},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(300.dp)
        )
        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedTextField(
            value = "Descripcion",
            onValueChange = {},
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(300.dp)
                .height(150.dp)
        )
        SimpleExposedDropdownMenuEditar(
            Error = false,
            accion = {},
            cadena = "",
            opciones = listOf(),
            contenido = "Gimnasio"
        )
        //HorizontalDivider(thickness = 2.dp)
        DatePickerFieldToModalonChange(
            label = "",
            Error = false,
            accion = {},
            string = "fecha",
        )
        Row {
            SimpleExposedDropdownMomentoDia(
                Error = false,
                accion = {},
                cadena = "Momento del dia",
                contenido = ""
            )
        }


    }
}