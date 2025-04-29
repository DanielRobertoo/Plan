package com.example.postlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.domain.model.post
import com.example.postlist.usecase.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(PostListState())
    fun getPosts() {
        TODO("Not yet implemented")
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



}