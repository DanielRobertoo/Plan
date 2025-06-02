package com.example.requestlist.usecase

import com.example.domain.model.request


data class requestListState(
    val loading: Boolean = false,
    val noData: Boolean = false,
    val requests: List<request> = emptyList(),
)