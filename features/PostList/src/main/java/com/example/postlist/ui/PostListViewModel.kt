package com.example.postlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.domain.model.post
import com.example.postlist.usecase.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(PostListState())

    fun getPosts() {
        viewModelScope.launch {
            state = state.copy(posts = client.postgrest.from("post").select().decodeList<post>())
        }

    }

    fun SendRequest(post: post) {
        state = state.copy(postToJoin = post)
    }

    fun reset() {
        state = state.copy(postToJoin = null)
    }

    fun findUserById(id: String): String {
        return ""
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



}