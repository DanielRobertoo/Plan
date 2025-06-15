package com.example.chat.ui.ChatView

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.base.composables.LoadingUi
import com.example.chat.ui.base.AlertDialogYesNo
import com.example.chat.usecase.ChatState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel, goBack: () -> Unit, modifier: Modifier, idChat: Int, name: String) {
    LaunchedEffect(Unit) {
        viewModel.getMessagesFirstTime(idChat)
        viewModel.getMessages(idChat)
    }
    when {
        viewModel.state.onActiveBlock -> AlertDialogYesNo(
            title = "BLOQUEAR USUARIO",
            message = "Seguro quieres bloquear a este usuario?",
            onAccept = { viewModel.blockUser() },
            onDismiss = { viewModel.dismissBlock() }
        )
        viewModel.state.loading -> LoadingUi()
        else -> {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(name) },
                        navigationIcon = {
                            IconButton(
                                onClick = { goBack() },
                                content = {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = ""
                                    )
                                }
                            )
                        }
                    )
                }
            ) {
                Box(modifier = Modifier.padding(it).fillMaxSize(), contentAlignment = Alignment.Center){
                    ChatContent(
                        chatEvents = ChatEvents(
                            onTextChange = viewModel::onMessageTextChange,
                            onMessageSent = viewModel::onMessageTextSent
                        ),
                        state = viewModel.state,
                    )
                }

            }

        }
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
                    state.mensajes
                ) { item ->
                    if (item.sender_id == state.idUser)
                        mensajeEnviado(item.content)
                    else
                        mensajeRecibido(item.content)

                }
            }
        }
        Row {
            TextField(
                value = state.messageToSend,
                onValueChange = { chatEvents.onTextChange(it) },
                modifier = Modifier
                    .height(50.dp)
                    .width(350.dp)
            )
            Spacer(Modifier.padding(5.dp))
            FloatingActionButton(onClick = { chatEvents.onMessageSent() }) {
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
            .padding(5.dp), horizontalArrangement = Arrangement.End
    ) {
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
            .padding(5.dp), horizontalArrangement = Arrangement.Start
    ) {
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun preview() {
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
                value = "",
                onValueChange = {}
            )
            Spacer(Modifier.padding(5.dp))
            FloatingActionButton(onClick = {}) {
                Icon(Icons.AutoMirrored.Filled.Send, "")
            }
        }

    }
}