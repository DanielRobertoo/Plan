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
import com.example.domain.model.request
import com.example.domain.model.user
import com.example.postlist.usecase.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(val preferences: UserPreferences) : ViewModel() {

    var state by mutableStateOf(PostListState())

    fun getPosts() {
        viewModelScope.launch {
            val name = client.postgrest.from("user").select(){
                filter {
                    eq("email", preferences.getEmail()!!)
                }
            }.decodeSingle<user>().user_name
            state = state.copy(posts = client.postgrest.from("post").select(){
                filter {
                    neq("post_creator_username", name)
                }
            }.decodeList<post>())
            Log.d("Lista post", state.posts.toString())
        }

    }

    fun SendRequest(post: post) {
        viewModelScope.launch {
            val countId = client.postgrest.from("request").select().decodeList<request>().count()
            val userRequest = client.postgrest.from("user").select(){
                filter {
                    eq("email", preferences.getEmail()!!)
                }
            }.decodeSingle<user>()

            val userOwner = client.postgrest.from("user").select(){
                filter {
                    eq("user_name", post.post_creator_username)
                }
            }.decodeSingle<user>()
            val request = request(
                id = countId,
                created_at = LocalDate.now().toString(),
                gym = post.gym,
                state = 0,
                date = post.date,
                title = post.title,
                username_request = post.post_creator_username,
                id_guest = userRequest.id,
                id_owner = userOwner.id
            )
            client.postgrest.from("request").insert(request)
            state = state.copy(postToJoin = null)
        }

    }

    fun reset() {
        state = state.copy(postToJoin = null, loading = false, cerrarSesion = false)
    }



    fun askRequest(id: String) {
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