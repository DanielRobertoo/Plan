package com.example.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class user(
    val id: Int,
    val name: String,
    val surname: String,
    val user_name:String,
    val age:String,
    val created_at: String,
    val enabled: Int,
    val email: String
)