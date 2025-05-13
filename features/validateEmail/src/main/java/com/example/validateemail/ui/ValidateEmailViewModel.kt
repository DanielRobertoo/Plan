package com.example.validateemail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.validateemail.usecase.ValidateEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidateEmailViewModel @Inject constructor()
    : ViewModel()
{
    var state by mutableStateOf(ValidateEmailState())

    fun onCodeChange(string: String){
        state = state.copy(code = string)
    }

    fun validateEmail(){
        viewModelScope.launch {
            val result = client.from("code_verification").select {
                filter {
                    eq("code", state.code)
                }
            }.data

        }

    }
}