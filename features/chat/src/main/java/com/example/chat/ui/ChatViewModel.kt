package com.example.chat.ui.ChatView

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chat.usecase.ChatState

class ChatViewModel : ViewModel() {
    var chatState by mutableStateOf(ChatState())

    fun onMessageTextChange(mensaje:String){
        chatState = chatState.copy(messageToSend = mensaje)
    }

    fun blockUser() {
        TODO("Not yet implemented")
    }

    fun dismissBlock() {
        TODO("Not yet implemented")
    }

    fun onMessageTextSent() {
        TODO("Not yet implemented")
    }
}