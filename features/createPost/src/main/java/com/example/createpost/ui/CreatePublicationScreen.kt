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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.chat.ui.base.AlertDialogOK
import com.example.chat.ui.base.DatePickerFieldToModalonChange
import com.example.chat.ui.base.SimpleExposedDropdownGimnasio
import com.example.chat.ui.base.SimpleExposedDropdownMenuEditar
import com.example.chat.ui.base.SimpleExposedDropdownMomentoDia
import com.example.createpost.usecase.CreatePublicationState
import com.example.domain.model.DataBase.Gym
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

        viewModel.state.success -> {
            AlertDialogOK(
                title = "Publicación creada",
                message = "Se ha creado una nueva publicación",
                onDismiss = { goBack() })
            goBack()
        }

        else -> CreatePulicationContent(viewModel.state, events = CreationPublicationEvents(
            onDescriptionChange = viewModel::onDescriptionChange,
            onHourBeginChange = viewModel::onTimeSetChange,
            onDateChange = viewModel::onDateChange,
            onTitleChange = viewModel::onTiTleChange,
            onGymChange = viewModel::onGymChange,
            onMomentDayChange = viewModel::onMomentDayChange
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
)

@Composable
fun CreatePulicationContent(
    state: CreatePublicationState,
    events: CreationPublicationEvents,
    goBack: () -> Unit,
    gymList: List<Gym>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
    ) {

        OutlinedTextField(
            value = state.title,
            onValueChange = { events.onTitleChange(it) },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.width(300.dp),
            placeholder = { Text("Titulo") }
        )
        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedTextField(
            value = state.description,
            onValueChange = { events.onDescriptionChange },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .width(300.dp)
                .height(150.dp),
            placeholder = { Text("Descripción") }
        )
        SimpleExposedDropdownGimnasio(
            Error = false,
            accion = { events.onGymChange(it) },
            cadena = state.gym,
            contenido = "Gimnasio",
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
                accion = {events.onMomentDayChange(it)},
                contenido = state.momentDay,
                cadena = "Momento del dia"
            )
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