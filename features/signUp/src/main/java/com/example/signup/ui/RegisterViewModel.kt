package com.example.login.ui.register

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.utils.SupabaseClient.client
import com.example.base.utils.validateEmail
import com.example.domain.Email.enviarCodigoPorEmail
import com.example.domain.model.DataBase.code_verification
import com.example.domain.model.user
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RegisterViewModel @Inject constructor() : ViewModel() {

    var state by mutableStateOf(AccountRegisterState())

    fun onUserNameChange(s: String) {
        state = state.copy(username = s)
    }

    fun onEmailChange(s: String) {
        state = state.copy(email = s)
    }

    fun onNameChange(s: String) {
        state = state.copy(name = s)
    }

    fun onSurnameChange(s: String) {
        state = state.copy(surname = s)
    }

    fun onPasswordChange(s: String) {
        state = state.copy(password = s)
    }

    fun onBirthdayChange(s: String) {
        state = state.copy(birthday = s)
    }

    fun ageCalculator(): String {
        if (state.birthday == "") {
            state = state.copy(isEmptyFields = true)
            return ""
        }
        return Period.between(
            LocalDate.parse(
                state.birthday,
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
            ), LocalDate.now()
        ).years.toString()
    }

    fun onRegisterClick() {
        viewModelScope.launch {
            try {
                if (!validateEmail(state.email)) {
                    state = state.copy(isEmailInvalid = true, isLoading = false)
                    return@launch
                }
                if (client.postgrest.from("user").select() {
                        filter {
                            and {
                                eq("email", state.email)
                                eq("enabled", 1)
                            }

                        }
                    }.decodeList<user>().count() != 0){
                    state = state.copy(emailExist = true, isLoading = false)
                    return@launch
                }
                if (state.password.length < 6){
                    state = state.copy(passwordLenghtError = true, isLoading = false)
                    return@launch
                }
                if (client.postgrest.from("user").select() {
                        filter {
                            and {
                                eq("user_name", state.username)
                            }

                        }
                    }.decodeList<user>().count() != 0){
                    state = state.copy(usernameExist = true)
                    return@launch
                }
                    state = state.copy(isLoading = true)
                var codigo = ""
                for (i in 0..5) {
                    codigo += Random.nextInt(10).toString()
                }

                val listaCodeVerification =
                    client.postgrest.from("code_validation").select()
                        .decodeList<code_verification>()

                val id_User = client.postgrest.from("user").select().decodeList<user>().count()

                listaCodeVerification.forEach {
                    Log.d("item", "$it")
                    if (it.email == state.email && it.validate == 1) {
                        state = state.copy(emailExist = true, isLoading = false)
                        Log.d("comprobacion", "$state")
                        return@launch
                    }
                }
                var userToSend = user(
                    age = ageCalculator(),
                    created_at = LocalDate.now().toString(),
                    name = state.name,
                    surname = state.surname,
                    user_name = state.username,
                    id = id_User,
                    enabled = 0,
                    email = state.email
                )
                if (client.postgrest.from("user").select() {
                        filter {
                            and {
                                eq("email", state.email)
                            }

                        }
                    }.decodeList<user>().count() == 0){

                    client.postgrest.from("user").insert(userToSend)
                }
                else{
                    var userToUpdate = client.postgrest.from("user").select() {
                        filter {
                            and {
                                eq("email", state.email)
                            }

                        }
                    }.decodeSingle<user>().id
                    userToSend = userToSend.copy(id = userToUpdate)
                    client.postgrest.from("user").update(userToSend){
                        filter {
                            eq("id",userToSend.id)
                        }
                    }
                }
                enviarCodigoPorEmail(
                    codigo = codigo,
                    toName = state.name,
                    onError = {
                        state = state.copy(emailNotExist = true)
                    },
                    toEmail = state.email,
                    onSuccess = { insertCodeVerificationSupabase(state.email, codigo) }
                )

                state = state.copy(isLoading = false, success = true, userId = userToSend.id.toString())
            } catch (e: Exception) {
                Log.d("SIGN UP", "${e.message}")
            }
        }
    }

    fun insertCodeVerificationSupabase(email: String, code: String) {
        viewModelScope.launch {
            var listCheck =client.postgrest.from("code_validation").select(){
                filter {
                    eq("email", email)
                }
            }.decodeList<code_verification>()
            if (
                listCheck.count()==1
            ){
                var cv = listCheck[0].copy(
                    code = code
                )
                client.postgrest.from("code_validation").update(cv){
                    filter {
                        eq("id", cv.id)
                    }
                }
            }
            else{
                val listaCodeVerification =
                    client.postgrest.from("code_validation").select().decodeList<code_verification>()

                val id = listaCodeVerification.count()
                Log.d("count code_validation_id", id.toString())
                client.from("code_validation").insert(
                    code_verification(id, email, code, 0)
                )
            }

        }

    }

    fun onReset() {
        state = state.copy(isLoading = false, emailExist = false, isEmailInvalid = false,passwordLenghtError = false, usernameExist = false, emailNotExist = false)
    }

}