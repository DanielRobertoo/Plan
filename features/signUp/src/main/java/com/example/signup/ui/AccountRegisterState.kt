package com.example.login.ui.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

data class AccountRegisterState (
    //RNREGISTER_1 : Formato Passqword incorrecto
    // 8 caracteres mínimo (ya se contempla el nulo)
    // A-> 1 carácter mayúscula
    // 2-> mínimio 1 carácter número
    // ? -> 1 caracter especial
    val email:String = "",

    val password: String = "",

    val username: String = "",

    val name: String = "",

    val surname: String = "",

    val isDatePickerVisible: MutableState<Boolean> = mutableStateOf(false),

    val birthday: Date? = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()),

    val isEmailError: Boolean = false,

    val isSurnameError: Boolean = false,

    val isNameError: Boolean = false,

    val isPasswordError: Boolean = false,
    val isUserError: Boolean = false,
    val isBirthdayError: Boolean = false,



    val isEmpty: String? = null,
    val isEmptyFields:Boolean = false,

    val passwordErrorFormat: String = "",

    //RNREGISTER_2 : Formato email incorrecto
    // pattern y matcher
    val emailErrorFormat: String = "",


    //RNREGISTER_3 : Formato name user incorrecto
    // pattern y matcher
    val nameErrorFormat: String = "",

    //RNREGISTER_4: Nombre y apellidos del usuario no nulo
    val userErrorFormat: String = "",

    //RNREGISTER_5: Fecha posterior a la actual
    val dateErrorFormat: String = "",

    val emailError: Int? = null,
    val passwordError: Int? = null,

    val surnameErrorFormat: String = "",



    //RNREGISTER_6: Cuenta ya existe (name user, email)
    val accountExitsError: Boolean = false,

    //RNREGISTER_7:Error que venga de la infraestructura
    // (bases de dats, netflix...)
    val serverError: String? = null,

    //RNREGISTER_8 : Success
    var success: Boolean = false,



    //Requerimientos NO Funcional
    //RNFLOGIN_1 : Tiempo de espera de conexión
    val isLoading: Boolean = false,

    ) {


}