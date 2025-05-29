package com.example.postlist.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.preferences.core.preferencesOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.datasore.UserPreferences
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.post
import com.example.postlist.usecase.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel() {

    var state by mutableStateOf(PostListState())

    fun getPosts() {
        viewModelScope.launch {
            state = state.copy(posts = client.postgrest.from("post").select().decodeList<post>())
            Log.d("Lista post", state.posts.toString())
        }

    }

    fun SendRequest(post: post) {
        state = state.copy(postToJoin = post)
    }

    fun reset() {
        state = state.copy(postToJoin = null, loading = false, cerrarSesion = false)
    }



    fun request(id: String) {
        viewModelScope.launch {
            client.postgrest.from("post").select().decodeList<post>().forEach {
                if (it.id.toString() == id) {
                    state = state.copy(postToJoin = it)
                }
            }

        }

    }

    fun logOut() {
        viewModelScope.launch {
            preferences.clearSession()
        }
    }

    fun setLogOut() {
        state = state.copy(cerrarSesion = true)
    }


}