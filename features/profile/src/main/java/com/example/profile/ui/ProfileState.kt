package com.example.profile.ui

import com.example.domain.model.post
import com.example.domain.model.user

data class ProfileState(
    val postToCheck: post? = null,
    val posts: MutableList<post> = mutableListOf(),
    val user: user? = null
)