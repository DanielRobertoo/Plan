package com.example.createpost.usecase

data class CreatePublicationState(
    val title:String = "",
    val description:String = "",
    val date: String = "",
    val hourBegin: String = "",
    val minutesBegin:String = "",
    val gym: String = "",
    val meetPoint: String = "",
    val momentDay: String = "",

    val errorFields: Boolean = false,
    val success:Boolean = false,
    val publications: List<Any> = listOf()
)