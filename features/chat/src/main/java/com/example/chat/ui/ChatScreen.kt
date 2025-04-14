package com.example.chat.ui.ChatView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chat.ui.base.AlertDialogYesNo
import com.example.chat.usecase.ChatState


@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    when {
        viewModel.chatState.onActiveBlock -> AlertDialogYesNo(
            title = "BLOQUEAR USUARIO",
            message = "Seguro quieres bloquear a este usuario?",
            onAccept = {viewModel.blockUser()},
            onDismiss = {viewModel.dismissBlock()}
            )

        else -> ChatContent(chatEvents = ChatEvents(
            onTextChange = viewModel::onMessageTextChange,
            onMessageSent = viewModel::onMessageTextSent
        ),
            state = viewModel.chatState
        )
    }
}

data class ChatEvents(
    val onTextChange: (String) -> Unit,
    val onMessageSent: () -> Unit
)

@Composable
fun ChatContent(chatEvents: ChatEvents, state: ChatState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column {
            LazyColumn(modifier = Modifier.padding(10.dp)) {
                items(
                    listOf(
                        Mensaje("2", "Hola"),
                        Mensaje(
                            "1",
                            "Texto Texto Texto Texto Texto Texto Texto Texto Texto Texto Texto Texto Texto Texto Texto "
                        ),
                        Mensaje("2", "Que tal"),
                    )
                ) { item ->
                    if (item.identificador == "1")
                        mensajeEnviado(item.mensaje)
                    else
                        mensajeRecibido(item.mensaje)

                }
            }
        }
        Row {
            TextField(
                value = state.messageToSend,
                onValueChange = {chatEvents.onTextChange(it)}
            )
            Spacer(Modifier.padding(5.dp))
            FloatingActionButton(onClick = {chatEvents.onMessageSent()}) {
                Icon(Icons.AutoMirrored.Filled.Send, "")
            }
        }

    }
}


data class Mensaje(val identificador: String, val mensaje: String)

@Composable
fun mensajeEnviado(mensaje: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.End) {
        ElevatedCard(modifier = Modifier.wrapContentSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Text(mensaje)
            }
        }
    }
}

@Composable
fun mensajeRecibido(mensaje: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(5.dp), horizontalArrangement = Arrangement.Start) {
        ElevatedCard(modifier = Modifier.wrapContentSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(mensaje)
            }

        }
    }
}