package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class request(
    val id: Int,
    val usuario_id: Int,
    val publicacion_id: Int,
    val estado : Int,
    val fecha: String
)