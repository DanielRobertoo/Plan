package com.example.login.ui.register

import android.graphics.BitmapFactory.Options
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
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Count
import io.github.jan.supabase.postgrest.query.filter.TextSearchType
import io.github.jan.supabase.postgrest.query.request.SelectRequestBuilder
import io.github.jan.supabase.realtime.selectAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.modules.SerializersModule
import okhttp3.internal.checkOffsetAndCount
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

             val id = client.postgrest.from("code_validation").select().decodeList<code_verification>().count()

             client.from("code_validation").upsert(
                 code_verification(id, email, code)
             )
         }

    }

}