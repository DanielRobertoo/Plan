package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class chat(
    val id:Int,
    val created_at:String,
    val user1_id: Int,
    val user2_id: Int
)