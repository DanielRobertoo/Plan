package com.example.editpost.usecase

import com.example.domain.model.post

data class EditPostState(
    val title:String = "",
    val description:String = "",
    val date: String = "",
    val gym: String = "",
    val momentDay: String = "",
    val creator_post_username: String = "",


    val errorFields: Boolean = false,
    val success:Boolean = false,
    val publications: MutableList<post> = mutableListOf(),
    val errorDate: Boolean = false
)