package com.example.postlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.postlist.usecase.PostListState

class PostListViewModel : ViewModel(){
    var state by mutableStateOf(PostListState())

}