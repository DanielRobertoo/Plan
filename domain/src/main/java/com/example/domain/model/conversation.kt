package com.example.domain.model

data class Conversation(
    val id: String,
    val user1Id:String,
    val user2Id:String,
    val createdAt:String
)