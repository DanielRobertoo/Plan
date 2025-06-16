package com.example.chat.ui.ChatView

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.chat.usecase.ChatState
import com.example.domain.model.message
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val preferences: UserPreferences): ViewModel() {
    var state by mutableStateOf(ChatState())
    var idUser: Int? = null

    fun onMessageTextChange(mensaje:String){
        state = state.copy(messageToSend = mensaje)
    }

    fun blockUser() {
        TODO("Not yet implemented")
    }

    fun dismissBlock() {
        TODO("Not yet implemented")
    }

    fun onMessageTextSent() {
        viewModelScope.launch {
            if (state.messageToSend == ""){
                return@launch
            }
            val mensaje = message(
                id = client.postgrest.from("message").select().decodeList<message>().count(),
                send_at = LocalDate.now().toString(),
                content = state.messageToSend,
                sender_id = state.idUser!!,
                conversation_id = state.idChat!!
            )
            val lista = state.mensajes.toMutableList()
            lista.add(mensaje)
            state = state.copy(mensajes = lista, messageToSend = "")

            client.postgrest.from("message").insert(mensaje)
        }


    }

    fun getMessages(_idChat: Int) {
        state = state.copy(idChat = _idChat)
        viewModelScope.launch {
            if (preferences.getEmail() == null){
                Log.d("get messages", "get")
                return@launch
            }
            Log.d("get messages", "get")
            while (true){
                delay(2000)
                state = state.copy(
                    mensajes = client.postgrest.from("message").select(){
                        filter {
                            eq("conversation_id", _idChat)
                        }
                    }.decodeList<message>(),
                    idUser = client.postgrest.from("user").select(){
                        filter {
                            eq("email",preferences.getEmail()!!)
                        }
                    }.decodeSingle<user>().id
                )
            }


        }
    }
    fun getMessagesFirstTime(_idChat: Int) {
        state = state.copy(idChat = _idChat, loading = true)
        viewModelScope.launch {
                state = state.copy(
                    mensajes = client.postgrest.from("message").select(){
                        filter {
                            eq("conversation_id", _idChat)
                        }
                    }.decodeList<message>(),
                    idUser = client.postgrest.from("user").select(){
                        filter {
                            eq("email",preferences.getEmail()!!)
                        }
                    }.decodeSingle<user>().id,
                    loading = false
                )
        }
    }
}