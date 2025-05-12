package com.example.login.ui.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.base.utils.validateEmail
import com.example.base.utils.validatePassword
import com.example.domain.Email.enviarCodigoPorEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.SerializersModule
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {
    
    var state by mutableStateOf(AccountRegisterState())
    
    fun onUserNameChange(s: String) {
        state = state.copy(username = s)
    }

    fun onEmailChange(s: String) {
        state = state.copy(email = s)
    }

    fun onNameChange(s: String) {
        state = state.copy(name = s)
    }

    fun onSurnameChange(s: String) {
        state = state.copy(surname = s)
    }

    fun onPasswordChange(s: String) {
        state = state.copy(password = s)
    }

    fun onBirthdayChange(s: String) {
        state = state.copy(birthday = s)
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                client.auth.signUpWith(Email) {
                    email = state.email
                    password = state.password
                }
                val codigo = Random(1).nextInt(0, 100000).toString().padStart(6, '0')
                enviarCodigoPorEmail(
                    codigo = codigo,
                    toName = state.name,
                    onError = {},
                    toEmail = state.email,
                    onSuccess = { insertCodeVerificationSupabase(state.email, codigo) }
                )
                state = state.copy(isLoading = false, success = true)
            } catch (e: Exception) {
                Log.d("SIGN UP", "${e.message}")
            }
        }
    }

     fun insertCodeVerificationSupabase(email: String, code: String){
         viewModelScope.launch {
             val id = client.from("code_users").select {
                 count
             }

             client.from("code_users").insert(
                 listOf(id, email, code)
             )
         }

    }

}