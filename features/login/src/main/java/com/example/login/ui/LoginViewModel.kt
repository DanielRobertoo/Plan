package com.example.login.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.ktor.util.reflect.loadServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor( private val userPrefs:UserPreferences) : ViewModel() {
    var state by mutableStateOf(AccountLoginState())


    fun checkAndAsign(accion: () -> Unit){

        viewModelScope.launch {
            state = state.copy(isLoading = true)
            delay(500)
            if (userPrefs.getPassword() != null && userPrefs.getEmail() != null){
                accion()
            }
            state = state.copy(isLoading = false)
        }


    }

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun reset(){
        state = state.copy(isErrorAccount = false)
    }

    fun onLoginClick(goList: () -> Unit) {
        viewModelScope.launch {
            var succes: Boolean = true
            Log.d("LOGIN", "${state.email} ${state.password}")
            try {
                state = state.copy(isLoading = true)
                client.auth.signInWith(Email) {
                    email = state.email
                    password = state.password
                }

                Log.d("LOGIN", "${state}")
            } catch (e: Exception) {
                state = state.copy(isErrorAccount = true, isLoading = false)
                Log.d("LOGIN", "${e.message}")
                succes = false
            }

            if (succes){
                userPrefs.saveUserSession(state.email, state.password)
                goList()
            }
        }
    }
}