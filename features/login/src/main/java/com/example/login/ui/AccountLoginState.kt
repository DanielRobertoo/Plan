package com.example.login.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

data class AccountLoginState (
    val email:String = "",
    val password:String = "",

    val isDialogVisible:Boolean = false,

    val passwordErrorFormat: String = "",

    //RNLOGIN_2 : Formato email incorrecto
    // pattern y matcher
    val emailErrorFormat: String = "",

    //RNLOGIN_3 : Usuario no registrado
    val userError: String? = null,


    //RNLOGIN_4 : Success
    val success: Boolean = false,

    //Requerimientos NO Funcional
    //RNFLOGIN_1 : Tiempo de espera de conexión
    var isLoading: Boolean = false,

    //RNFLOGIN_2 : Error de conexión
    val isOffline: Boolean = false,

    //Se crea los booleanos necesarios para saber si hay un error en el email
    val isEmailError:Boolean = false,

    val isPasswordError:Boolean = false,

    //val accountException: AccountException = AccountException.Idle,

    var isErrorAccount:Boolean = false

)