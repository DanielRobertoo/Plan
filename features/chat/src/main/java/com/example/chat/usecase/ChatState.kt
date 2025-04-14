package com.example.chat.usecase

import com.example.domain.Mensaje

data class ChatState(
    val mensajes: List<Mensaje> = listOf(),
    val messageToSend:String = "",
    val idChat: String = "",

    val onActiveBlock: Boolean = false
)