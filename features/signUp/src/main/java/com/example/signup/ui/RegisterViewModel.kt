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
import com.example.domain.model.code_verification
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.OtpType
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Count
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
                var codigo = ""
                for (i in 0..5){
                    codigo += Random.nextInt(10).toString()
                }
                enviarCodigoPorEmail(
                    codigo = codigo,
                    toName = state.name,
                    onError = {},
                    toEmail = state.email,
                    onSuccess = { insertCodeVerificationSupabase(state.email, codigo) }
                )
                insertCodeVerificationSupabase(state.email, codigo)
                state = state.copy(isLoading = false, success = true)
            } catch (e: Exception) {
                Log.d("SIGN UP", "${e.message}")
            }
        }
    }

     fun insertCodeVerificationSupabase(email: String, code: String){
         viewModelScope.launch {

             val id = client.postgrest.from("code_verification").table.length

             client.from("code_verification").insert(
                 code_verification(id.toString(), email, code)
             )
         }

    }

}