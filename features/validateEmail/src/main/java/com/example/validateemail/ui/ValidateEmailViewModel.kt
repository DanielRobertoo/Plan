package com.example.validateemail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.code_verification
import com.example.validateemail.usecase.ValidateEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidateEmailViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ValidateEmailState())

    var _email: String = ""
    var _password: String = ""

    fun onCodeChange(string: String) {
        state = state.copy(code = string)
    }

    fun assignEmail(email: String, password: String){
        _email = email
        _password = password
    }

    fun validateEmail(goLogin: () -> Unit) {

        viewModelScope.launch {
            val lista = client.from("code_validation").select().decodeList<code_verification>()
            val match = lista.any {
                it.email == _email && it.code == state.code
            }

            if (match) {
                client.auth.signUpWith(Email) {
                    email = _email
                    password = _password
                }
                goLogin()
            } else {
                state = state.copy(fail = true)
            }
        }
    }

    fun reset() {
        state = state.copy(success = false, fail = false)
    }
}