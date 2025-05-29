package com.example.plan.ui.graph

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.chat.ui.ChatView.ChatScreen
import com.example.chat.ui.ChatView.ChatViewModel
import com.example.chat.ui.CreatePublicacion.CreatePublicationScreen
import com.example.chat.ui.CreatePublicacion.CreatePublicationViewModel
import com.example.login.ui.login.LoginScreen
import com.example.login.ui.login.LoginViewModel
import com.example.login.ui.register.RegisterScreen
import com.example.login.ui.register.RegisterViewModel
import com.example.postlist.ui.PostListScreen
import com.example.postlist.ui.PostListViewModel
import com.example.validateemail.ui.ValidateEmailScreen
import com.example.validateemail.ui.ValidateEmailViewModel


@Composable
fun PlanScreen(navController: NavHostController) {
    var actual = remember { mutableStateOf("login") }
    Scaffold(
        bottomBar = {
            if (actual.value != "login" && actual.value != "signUp" && actual.value != "validate") {
                NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                    NavigationBarItem(
                        selected = actual.value=="list",
                        onClick = { navController.navigate("list") },
                        icon = { Icon(Icons.Filled.Home, null) },
                        label = { Text(text = "") }
                    )
                    NavigationBarItem(
                        selected = actual.value=="request",
                        onClick = { /*navController.navigate("request")*/ },
                        icon = { Icon(Icons.Filled.Email, null) },
                        label = { Text(text = "") }
                    )
                    NavigationBarItem(
                        selected = actual.value=="chat",
                        onClick = { /*navController.navigate("chat")*/ },
                        icon = { Icon(Icons.AutoMirrored.Filled.Send, null) },
                        label = { Text(text = "") }
                    )
                    NavigationBarItem(
                        selected = actual.value=="perfil",
                        onClick = { /*navController.navigate("perfil")*/ },
                        icon = { Icon(Icons.Filled.AccountCircle, null) },
                        label = { Text(text = "") }
                    )
                }
            } else {

            }
        }
    ) {


        NavHost(startDestination = "login", navController = navController, modifier = Modifier.padding(it)) {
            composable(route = "list") {
                actual.value = "list"
                Log.d("actual", "$actual")
                PostListScreen(
                    viewModel = hiltViewModel<PostListViewModel>(),
                    goAdd = { navController.navigate("add") },
                    goRequest = {},
                    goback = {navController.navigate("login")}
                )
            }
            composable(route = "add") {
                actual.value = "add"
                CreatePublicationScreen(
                    viewModel = hiltViewModel<CreatePublicationViewModel>(),
                    goBack = { navController.popBackStack() },
                    modifier = Modifier
                )
            }
            composable(route = "chat") {
                actual.value = "chat"
                ChatScreen(
                    viewModel = hiltViewModel<ChatViewModel>(),
                    goBack = { navController.popBackStack() },
                    modifier = Modifier
                )
            }
            composable(route = "login") {
                actual.value = "login"
                LoginScreen(
                    viewModel = hiltViewModel<LoginViewModel>(),
                    email = "",
                    password = "",
                    goToRegister = { navController.navigate("signUp") },
                    goToListAccount = {
                        navController.navigate("list") {
                        }
                    },
                    goback = { navController.popBackStack() }
                )
            }

            composable(route = "signUp") {
                actual.value = "signUp"
                RegisterScreen(
                    viewModel = hiltViewModel<RegisterViewModel>(),
                    goToLogin = { navController.popBackStack() },
                    goValidate = { email: String, password: String, userId:String ->
                        navController.navigate("validateEmail/$email/$password/$userId")
                    },
                    modifier = Modifier
                )
            }

            composable(
                route = "validateEmail/{email}/{password}/{userId}",
                arguments = listOf(
                    navArgument("email") {
                        type = NavType.StringType
                    },
                    navArgument("password") {
                        type = NavType.StringType
                    },
                    navArgument("userId"){
                        type = NavType.StringType
                    }
                )
            ) { argumento ->
                val email = argumento.arguments?.getString("email")
                val password = argumento.arguments?.getString("password")
                val userInfo = argumento.arguments?.getString("userId")
                actual.value = "validate"
                ValidateEmailScreen(
                    viewModel = hiltViewModel<ValidateEmailViewModel>(),
                    goLogin = {
                        navController.navigate("login")
                    },
                    goBack = { navController.popBackStack() },
                    email = email!!,
                    password = password!!,
                    userId = userInfo!!
                )
            }

        }
    }
}