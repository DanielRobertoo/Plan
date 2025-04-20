package com.example.postlist.usecase

import com.example.domain.model.post

data class PostListState(
    val loading: Boolean = false,
    val noData: Boolean = false,
    val posts: List<post> = emptyList(),
    val postToJoin: post? = null,
    val sorted: Boolean = false
    )