package com.example.chat.ui.CreatePublicacion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.createpost.usecase.CreatePublicationState
import com.example.domain.model.DataBase.Gym
import com.example.domain.model.post
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreatePublicationViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel() {
    var state by mutableStateOf(CreatePublicationState())
    var username: String = ""




    var gym: List<Gym> = listOf()

    fun onTiTleChange(texto:String){
        state = state.copy(title = texto)
    }

    fun onDescriptionChange(texto:String){
        state = state.copy(description = texto)
    }

    fun onDateChange(texto:String){
        state = state.copy(date = texto)
    }

    fun onTimeSetChange(texto:String){
        state = state.copy(hourBegin = texto)
    }

    fun onGymChange(texto: String){
        state = state.copy(gym = texto)
    }

    fun resetError() {
        state = state.copy(errorFields = false)
    }

    fun onMomentDayChange(s: String) {
        state = state.copy(momentDay = s)
    }
    fun loadUserName(){
        viewModelScope.launch {
            Log.d("resultado usuarios", "${preferences.getEmail()}")
            client.from("user").select().decodeList<user>().forEach {
                Log.d("resultado usuarios", "$it")
                if (it.email == preferences.getEmail()) {
                    username = "${it.user_name}, ${it.age}"
                    return@forEach
                }
            }
        }
    }
    fun loadGym(){
        viewModelScope.launch {
            val lista = client.from("gym").select().decodeList<Gym>().toMutableList()
            Log.d("lista gimnasios", lista.toString())
            gym = lista.toList()
        }
    }

    fun checkFields() : Boolean{
        return state.title == "" && state.gym == ""
    }

    fun onCreatePost(accion: () -> Unit){
        viewModelScope.launch {
            val id:Int = client.postgrest.from("post").select().decodeList<post>().count()
            if (checkFields()){
                state = state.copy(errorFields = true)
                return@launch
            }
            val postToLoad = post(

                title = state.title,
                created_at = LocalDateTime.now().toString(),
                id = id,
                date = state.date,
                gym = state.gym,
                description = state.description,
                post_creator_username = username,
                moment_day = state.momentDay
            )
            state.publications.add(postToLoad)
            client.from("post").insert(postToLoad)
            accion()
        }
    }

}