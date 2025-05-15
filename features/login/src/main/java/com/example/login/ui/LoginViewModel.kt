package com.example.login.ui.login

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(AccountLoginState())


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
                goList()
                Log.d("LOGIN", "${state}")
            } catch (e: Exception) {
                state = state.copy(isErrorAccount = true, isLoading = false)
                Log.d("LOGIN", "${e.message}")
            }

        }
    }
}