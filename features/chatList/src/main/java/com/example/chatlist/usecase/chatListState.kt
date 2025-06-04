package com.example.chatlist.usecase

import com.example.domain.model.chat
import com.example.domain.model.post

data class ChatListState (
    val loading: Boolean = false,
    val noData: Boolean = false,
    val chats: List<chat> = emptyList(),
    val chatToOpen: post? = null,
    val listChatShow: List<chatToShow> = listOf()
)


data class chatToShow(
    val username: String = "",
    val lastMessage: String = "",
    val time: String = "",
    val idChat: Int? = null
)