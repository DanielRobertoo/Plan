package com.example.chatlist.usecase

import com.example.domain.model.chat
import com.example.domain.model.post

data class ChatListState (
    val loading: Boolean = false,
    val noData: Boolean = false,
    val chats: List<chat> = emptyList(),
    val chatToOpen: post? = null
)