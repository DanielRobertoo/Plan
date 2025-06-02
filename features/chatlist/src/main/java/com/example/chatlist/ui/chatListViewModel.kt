package com.example.chatlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.chatlist.usecase.ChatListState
import com.example.domain.model.chat
import com.example.domain.model.message
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class chatListViewModel @Inject constructor(preferences: UserPreferences) : ViewModel() {
    fun getNameSender(user2Id: Int): String {
        var name: String = ""
        viewModelScope.launch {
            client.postgrest.from("user").select().decodeList<user>().forEach {
                if (it.id == user2Id){
                    name = it.name
                }
            }
        }
        return name

    }

    fun getLastMessage(user2Id: Int): String {
        var mensaje = ""
        viewModelScope.launch {
           mensaje = client.postgrest.from("user").select().decodeList<message>().last().content
        }
        return mensaje
    }

    fun getPosts() {
        viewModelScope.launch {
            state = state.copy(chats = client.postgrest.from("chat").select().decodeList<chat>())
        }
    }

    var state by mutableStateOf(ChatListState())
    var idUser: Int? = null
    var email: String? = null

    init {
        viewModelScope.launch {
            email = preferences.getEmail()
            client.postgrest.from("user").select().decodeList<user>().forEach {
                if (it.email == email){
                    idUser = it.id
                    return@forEach
                }
            }
        }

    }

}