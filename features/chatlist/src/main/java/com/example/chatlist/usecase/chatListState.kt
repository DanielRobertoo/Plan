package com.example.chatlist.usecase

import com.example.domain.model.post

data class ChatListState (
    val loading: Boolean = false,
    val noData: Boolean = false,
    val chats: List<post> = emptyList(),
    val chatToOpen: post? = null
)