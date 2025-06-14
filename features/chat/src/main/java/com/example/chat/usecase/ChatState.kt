package com.example.chat.usecase

import com.example.domain.model._Mensaje
import com.example.domain.model.message

data class ChatState(
    val mensajes: List<message> = listOf(),
    val messageToSend:String = "",
    val idChat: Int? = null,
    val idUser: Int? = null,
    val onActiveBlock: Boolean = false,
    val loading: Boolean = false
)