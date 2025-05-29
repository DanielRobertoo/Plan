package com.example.domain.model

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class post(
    val id: Int,
    val post_creator_username:String,
    val title: String,
    val description:String,
    val gym:String = "",
    val date: String,
    val moment_day:String = "",
    val created_at:String
)