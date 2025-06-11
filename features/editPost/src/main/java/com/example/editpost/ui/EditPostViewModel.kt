package com.example.editpost.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.DataBase.Gym
import com.example.domain.model.post
import com.example.domain.model.user
import com.example.editpost.usecase.EditPostState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class EditPostViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel() {
    var state by mutableStateOf(EditPostState())
    var username: String = ""
    var idPost: Int? = null
    var gym: List<Gym> = listOf()

    fun loadPost(id: Int) {
        viewModelScope.launch {
            val postLoaded = client.postgrest.from("post").select() {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<post>()
            idPost = postLoaded.id
            state = state.copy(
                title = postLoaded.title,
                date = postLoaded.date,
                gym = postLoaded.gym,
                description = postLoaded.description,
                momentDay = postLoaded.moment_day,
                creator_post_username = postLoaded.post_creator_username,
            )
        }
    }

    fun onTiTleChange(texto: String) {
        state = state.copy(title = texto)
    }

    fun onDescriptionChange(texto: String) {
        state = state.copy(description = texto)
    }

    fun onDateChange(texto: String) {
        if (LocalDate.parse(texto, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                .isBefore(LocalDate.now())
        ) {
            state = state.copy(errorDate = true)
        } else {
            state = state.copy(date = texto)
        }

    }


    fun onGymChange(texto: String) {
        state = state.copy(gym = texto)
    }

    fun resetError() {
        state = state.copy(errorFields = false, errorDate = false)
    }

    fun onMomentDayChange(s: String) {
        state = state.copy(momentDay = s)
    }

    fun loadUserName() {
        viewModelScope.launch {
            Log.d("resultado usuarios", "${preferences.getEmail()}")
            client.from("user").select().decodeList<user>().forEach {
                Log.d("resultado usuarios", "$it")
                if (it.email == preferences.getEmail()) {
                    username = "${it.user_name}"
                    return@forEach
                }
            }
        }
    }



    fun loadGym() {
        viewModelScope.launch {
            val lista = client.from("gym").select().decodeList<Gym>().toMutableList()
            Log.d("lista gimnasios", lista.toString())
            gym = lista.toList()
        }
    }

    fun checkFields(): Boolean {
        return state.title == "" && state.gym == ""
    }

    fun onEditPost(accion: () -> Unit) {
        viewModelScope.launch {
            if (checkFields()) {
                state = state.copy(errorFields = true)
                return@launch
            }
            val postToLoad = post(

                title = state.title,
                created_at = LocalDateTime.now().toString(),
                id = idPost!!,
                date = state.date,
                gym = state.gym,
                description = state.description,
                post_creator_username = username,
                moment_day = state.momentDay
            )
            client.postgrest.from("post").update(postToLoad) {
                filter { eq("id", postToLoad.id) }
            }
            accion()
        }
    }


}