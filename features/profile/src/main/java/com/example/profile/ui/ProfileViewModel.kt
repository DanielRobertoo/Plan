package com.example.profile.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.post
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel() {
    var state by mutableStateOf(ProfileState())
    var username: String? = null

    fun getData() {
        state = state.copy(loading = true)
        Log.d("getData()", state.toString())
        viewModelScope.launch {
            username = client.postgrest.from("user").select() {
                filter {
                    eq("email", preferences.getEmail()!!)
                }
            }.decodeSingle<user>().user_name

            state = state.copy(
                posts = client.postgrest.from("post").select() {
                    filter {
                        eq("post_creator_username", username!!)
                    }
                }.decodeList<post>().toMutableList(),

                user = client.postgrest.from("user").select() {
                    filter {
                        eq("email", preferences.getEmail()!!)
                    }
                }.decodeSingle<user>(),

                loading = false
            )
        }
    }
    fun deletePost() {
        viewModelScope.launch {
            client.postgrest.from("post").delete {
                filter {
                    eq(
                        "id", state.postToDelete!!.id
                    )
                }
            }
            reset()
        }
    }

    fun setPostToDelete(id: Int) {
        viewModelScope.launch {
            state = state.copy(
                postToDelete = client.postgrest.from("post").select() {
                    filter {
                        eq("id", id)
                    }
                }.decodeSingle<post>()
            )
        }
    }

    fun reset() {
        state = state.copy(postToDelete = null)
    }


}