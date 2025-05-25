package com.example.createpost.usecase

import com.example.domain.model.post

data class CreatePublicationState(
    val title:String = "",
    val description:String = "",
    val date: String = "",
    val hourBegin: String = "",
    val minutesBegin:String = "",
    val gym: String = "",
    val momentDay: String = "",

    val errorFields: Boolean = false,
    val success:Boolean = false,
    val publications: MutableList<post> = mutableListOf()
)