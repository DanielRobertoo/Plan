package com.example.requestlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.chat
import com.example.domain.model.request
import com.example.domain.model.user
import com.example.requestlist.usecase.requestListState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel(){


    fun getRequest(){
        viewModelScope.launch {
            val idUsuario = client.postgrest.from("user").select(){
                filter {
                    eq("email", preferences.getEmail()!!)
                }
            }.decodeList<user>()
            val lista = client.postgrest.from("request").select(){
                filter {
                    and {
                        eq("id_owner",idUsuario[0].id)
                        eq("state", 0)
                    }

                }
            }.decodeList<request>()
            state = state.copy(requests = lista)
        }
    }
    fun onAccept(idRequest: Int, idOwner: Int, idGuest: Int) {
        viewModelScope.launch {
            var requestToModify = client.postgrest.from("request").select(){
                filter {
                    eq("id", idRequest)
                }
            }.decodeSingle<request>()
            requestToModify = requestToModify.copy(state = 1)
            client.postgrest.from("request").upsert(requestToModify)
            val chatCount = client.postgrest.from("chat").select().decodeList<chat>().count()
            val createChat = chat(
                id = chatCount,
                user1_id = idOwner,
                user2_id = idGuest,
                created_at = LocalDate.now().toString()
            )
            client.postgrest.from("chat").insert(createChat)
            var lista = state.requests.toMutableList()
            lista.removeAll{
                it.id == idRequest
            }
            state = state.copy(requests = lista.toList())
        }

    }



    fun onRefuse(idRequest: Int) {
        viewModelScope.launch {
            var requestToModify = client.postgrest.from("request").select(){
                filter {
                    eq("id", idRequest)
                }
            }.decodeSingle<request>()
            requestToModify = requestToModify.copy(state = -1)
            client.postgrest.from("request").upsert(requestToModify)
            var lista = state.requests.toMutableList()
            lista.removeAll{
                it.id == idRequest
            }
            state = state.copy(requests = lista.toList())
        }

    }

    var state by mutableStateOf(requestListState())

    
}