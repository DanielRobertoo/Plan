package com.example.chat.ui.CreatePublicacion

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.createpost.usecase.CreatePublicationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePublicationViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(CreatePublicationState())

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

}