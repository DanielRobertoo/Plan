package com.example.base.utils

fun validateEmail(email: String): Boolean{
    //Opcion 1
    //return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    //Opcion 2

    val emailPattern = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z]{2,}$"
    val regex = Regex(emailPattern) //Expresión regular
    return  regex.matches(email)

}

fun validatePassword(password: String): Boolean {
    val passwordRegex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
    return passwordRegex.matches(password)
}