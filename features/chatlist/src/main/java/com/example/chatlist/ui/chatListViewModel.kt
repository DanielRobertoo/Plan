package com.example.chatlist.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.chatlist.usecase.ChatListState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class chatListViewModel @Inject constructor() : ViewModel() {
    var state by mutableStateOf(ChatListState())
    var idUser: Int? = null

}