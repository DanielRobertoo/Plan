package com.example.validateemail.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.DataBase.code_verification
import com.example.domain.model.user
import com.example.validateemail.usecase.ValidateEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ValidateEmailViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ValidateEmailState())

    var _email: String = ""
    var _password: String = ""
    var _idUser: String = ""

    fun onCodeChange(string: String) {
        state = state.copy(code = string)
    }

    fun assignEmail(email: String, password: String, userId: String){
        _email = email
        _password = password
        _idUser = userId
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
                client.postgrest.from("user").select().decodeList<user>().forEach {
                    if (it.id.toString() == _idUser){
                        var userUpdate = it
                        userUpdate = userUpdate.copy(enabled = 1)
                        client.postgrest.from("user").upsert(userUpdate)
                    }
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