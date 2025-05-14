package com.example.domain.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class code_verification(
    val id: Int,
    val email: String,
    val code: String
)