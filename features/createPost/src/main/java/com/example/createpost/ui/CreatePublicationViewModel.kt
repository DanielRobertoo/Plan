package com.example.chat.ui.CreatePublicacion

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.createpost.usecase.CreatePublicationState
import com.example.domain.model.DataBase.Gym
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePublicationViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(CreatePublicationState())

    var gym: List<Gym> = listOf()

    fun onTiTleChange(texto:String){
        state = state.copy(title = texto)
    }

    fun onDescriptionChange(texto:String){
        state = state.copy(description = texto)
    }

    fun onGymChange(texto:String){
        state = state.copy(meetPoint = texto)
    }

    fun onDateChange(texto:String){
        state = state.copy(date = texto)
    }

    fun onTimeSetChange(texto:String){
        state = state.copy(hourBegin = texto)
    }

    fun resetError() {
        state = state.copy(errorFields = false)
    }

    fun onMomentDayChange(s: String) {
        state = state.copy(momentDay = s)
    }

    fun loadGym(){
        viewModelScope.launch {
            val lista = client.from("gym").select().decodeList<Gym>().toMutableList()
            Log.d("lista gimnasios", lista.toString())
            gym = lista.toList()
        }

    }

}