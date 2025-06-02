package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class message(
    val id:Int,
    val conversation_id :Int,
    val sender_id:Int,
    val content:String,
    val sent_at:String,
)