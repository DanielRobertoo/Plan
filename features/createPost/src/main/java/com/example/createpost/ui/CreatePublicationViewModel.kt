package com.example.chat.ui.CreatePublicacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CreatePublicationViewModel : ViewModel() {
    var state by mutableStateOf(CreatePublicationState())

    fun onTiTleChange(texto:String){
        state = state.copy(title = texto)
    }

    fun onDescriptionChange(texto:String){
        state = state.copy(description = texto)
    }

    fun onMeetPoingChange(texto:String){
        state = state.copy(meetpoint = texto)
    }

    fun onDateChange(texto:String){
        state = state.copy(fecha = texto)
    }

    fun onTimeSetChange(texto:String){
        state = state.copy(horaComienzo = texto)
    }

    fun onSportChange(texto: String){
        state = state.copy(sport = texto)
    }

    fun resetError() {
        state = state.copy(errorFields = false)
    }

}