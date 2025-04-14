package com.example.domain.model

data class message(
    val id:String,
    val conversationId :String,
    val senderId:String,
    val content:String,
    val sentAt:String
)