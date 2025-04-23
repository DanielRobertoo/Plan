package com.example.postlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.postlist.usecase.PostListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PostListViewModel @Inject constructor(): ViewModel(){
    fun getPosts() {
        TODO("Not yet implemented")
    }

    fun SendRequest() {
        TODO("Not yet implemented")
    }

    fun reset() {
        TODO("Not yet implemented")
    }
fun findUserById(id:String):String{
    return ""
}
    var state by mutableStateOf(PostListState())

}