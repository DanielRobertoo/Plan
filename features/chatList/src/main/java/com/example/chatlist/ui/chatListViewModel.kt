package com.example.chatlist.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.chatlist.usecase.ChatListState
import com.example.chatlist.usecase.chatToShow
import com.example.domain.model.chat
import com.example.domain.model.message
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel() {
    var state by mutableStateOf(ChatListState())
    var idUser: Int? = null
    var email: String? = null


    fun getNameSender(user2Id: Int): String {
        var name: String = ""

        viewModelScope.launch {
            client.postgrest.from("user").select().decodeList<user>().forEach {
                if (it.id == user2Id) {
                    name = it.name
                }
            }
        }
        return name

    }

    fun setLastMessage(){
        viewModelScope.launch {
            var list = mutableListOf<chatToShow>()
            state.chats.forEach {
                val idUse = if (it.user1_id == idUser) it.user2_id else it.user1_id
                var mensaje = ""
                var name: String = ""
                var time = ""
                val listaMensajes = client.postgrest.from("message").select(){
                    filter {

                        and {
                            eq("conversation_id", it.id)
                            eq("sender_id", idUse)
                        }
                    }
                }.decodeList<message>()
                if (listaMensajes.count() == 0){
                    mensaje = "* aun no hay mensajes en el chat *"
                    time = ""
                }
                else {
                    mensaje = listaMensajes.last().content
                    time = listaMensajes.last().send_at
                }

                client.postgrest.from("user").select().decodeList<user>().forEach {
                    if (it.id == idUse) {
                        name = it.name
                    }
                }
                val chatShow = chatToShow(
                    lastMessage = mensaje,
                    username = name,
                    time = time,
                    idChat = it.id
                )
                list.add(chatShow)


            }
            state = state.copy(listChatShow = list)

        }

    }



    fun getChat() {
        viewModelScope.launch {
            email = preferences.getEmail()
            client.postgrest.from("user").select().decodeList<user>().forEach {
                if (it.email == email) {
                    idUser = it.id
                    Log.d("idUser", idUser.toString())
                    return@forEach
                }
            }
            state = state.copy(
                chats = client.postgrest.from("chat").select() {
                    filter {
                        or {
                            eq("user1_id", idUser!!)
                            eq("user2_id", idUser!!)
                        }
                    }
                }.decodeList<chat>()
            )
            Log.d("lista Chats", state.toString())
            setLastMessage()
        }
    }




}