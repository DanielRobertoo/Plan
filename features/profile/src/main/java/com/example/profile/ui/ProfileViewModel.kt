package com.example.profile.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel(){
    var state by mutableStateOf(ProfileState())
    var username: String? = null


    fun getUserName(){
        viewModelScope.launch {
            username = client.postgrest.from("user").select(){
                filter {
                    eq("email", preferences.getEmail()!!)
                }
            }.decodeSingle<user>().user_name
        }
    }

    fun getPost(){
        viewModelScope.launch {
            client.postgrest.from("post").select(){
                filter {
                    eq("post_creator_username", username!!)
                }
            }
        }
    }
}