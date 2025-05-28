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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor( private val userPrefs:UserPreferences) : ViewModel() {
    var state by mutableStateOf(AccountLoginState())

    var goList: () -> Unit = {}

    fun checkAndAsign(accion: () -> Unit){
        state = state.copy(isLoading = true)
        goList = accion
        viewModelScope.launch {

            if (userPrefs.getPassword() != null && userPrefs.getEmail() != null){
                accion()
            }
        }
        state = state.copy(isLoading = false)

    }

    fun onEmailChange(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChange(password: String) {
        state = state.copy(password = password)
    }

    fun onLoginClick(goList: () -> Unit) {
        viewModelScope.launch {
            Log.d("LOGIN", "${state.email} ${state.password}")
            try {
                state = state.copy(isLoading = true)
                client.auth.signInWith(Email) {
                    email = state.email
                    password = state.password
                }
                userPrefs.saveUserSession(state.email, state.password)
                goList()
                Log.d("LOGIN", "${state}")
            } catch (e: Exception) {
                state = state.copy(isErrorAccount = true, isLoading = false)
                Log.d("LOGIN", "${e.message}")
            }

        }
    }
}