package com.example.domain.model.DataBase

import kotlinx.serialization.Serializable

@Serializable
data class Gym(
    val id: Int,
    val name: String,
    val address: String
)