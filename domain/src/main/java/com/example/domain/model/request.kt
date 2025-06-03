package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class request(
    val id: Int,
    val id_owner: Int,
    val id_guest: Int,
    val title : String,
    val date: String,
    val created_at: String,
    val username_request: String,
    val gym: String
)