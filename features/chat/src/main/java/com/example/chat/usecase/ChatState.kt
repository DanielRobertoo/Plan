package com.example.chat.usecase

import com.example.domain.model._Mensaje

data class ChatState(
    val mensajes: List<_Mensaje> = listOf(),
    val messageToSend:String = "",
    val idChat: String = "",

    val onActiveBlock: Boolean = false
)