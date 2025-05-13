package com.example.validateemail.usecase

data class ValidateEmailState(
    val code: String = "",
    val success: Boolean = false,
    val fail: Boolean = false
)