package com.example.domain.model

import java.util.Date

data class post(
    val id: String,
    val post_creator_id: String,
    val title: String,
    val description:String,
    val sport: String,
    val gym:String = "",
    val date: String,
    val hourBegin:String = "",
    val createdAt:String
)