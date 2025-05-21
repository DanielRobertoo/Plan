package com.example.domain.model.DataBase

import kotlinx.serialization.Serializable

@Serializable
data class code_verification(
    val id: Int,
    val email: String,
    val code: String,
    val validate: Int
)