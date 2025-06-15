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
import com.example.chatlist.ui.ChatListScreen
import com.example.chatlist.ui.ChatListViewModel
import com.example.editpost.ui.EditPostViewModel
import com.example.editpost.ui.EditPublicationScreen
import com.example.login.ui.login.LoginScreen
import com.example.login.ui.login.LoginViewModel
import com.example.login.ui.register.RegisterScreen
import com.example.login.ui.register.RegisterViewModel
import com.example.postlist.ui.PostListScreen
import com.example.postlist.ui.PostListViewModel
import com.example.profile.ui.ProfileScreen
import com.example.profile.ui.ProfileViewModel
import com.example.requestlist.ui.RequestListScreen
import com.example.requestlist.ui.RequestListViewModel
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
                        selected = actual.value == "list",
                        onClick = { navController.navigate("list") },
                        icon = { Icon(Icons.Filled.Home, null) },
                        label = { Text(text = "") }
                    )
                    NavigationBarItem(
                        selected = actual.value == "requestList",
                        onClick = { navController.navigate("requestList") },
                        icon = { Icon(Icons.Filled.Email, null) },
                        label = { Text(text = "") }
                    )
                    NavigationBarItem(
                        selected = actual.value == "chatList",
                        onClick = { navController.navigate("chatList") },
                        icon = { Icon(Icons.AutoMirrored.Filled.Send, null) },
                        label = { Text(text = "") }
                    )
                    NavigationBarItem(
                        selected = actual.value == "profile",
                        onClick = { navController.navigate("profile") },
                        icon = { Icon(Icons.Filled.AccountCircle, null) },
                        label = { Text(text = "") }
                    )
                }
            } else {

            }
        }
    ) {


        NavHost(
            startDestination = "login",
            navController = navController,
            modifier = Modifier.padding(it)
        ) {
            composable(route = "list") {
                actual.value = "list"
                Log.d("actual", "$actual")
                PostListScreen(
                    viewModel = hiltViewModel<PostListViewModel>(),
                    goAdd = { navController.navigate("add") },
                    goRequest = {},
                    goback = { navController.navigate("login") }
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
            composable(route = "chat/{id}/{name}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    },navArgument("name") {
                        type = NavType.StringType
                    }
                )
            ) {
                val id = it.arguments?.getInt("id")
                val name = it.arguments?.getString("name")
                actual.value = "chat"
                ChatScreen(
                    viewModel = hiltViewModel<ChatViewModel>(),
                    goBack = { navController.popBackStack() },
                    modifier = Modifier,
                    idChat = id!!,
                    name = name!!
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
                    goValidate = { email: String, password: String, userId: String ->
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
                    navArgument("userId") {
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
                    goBack = { navController.navigate("signUp") },
                    email = email!!,
                    password = password!!,
                    userId = userInfo!!
                )
            }

            composable(
                route = "requestList"
            ) {
                actual.value = "requestList"
                RequestListScreen(
                    viewModel = hiltViewModel<RequestListViewModel>()
                )
            }

            composable(route = "chatList") {
                actual.value = "chatList"
                ChatListScreen(
                    viewModel = hiltViewModel<ChatListViewModel>(),
                    goChat = { id: Int, name:String ->
                        navController.navigate("chat/${id}/${name}")
                    }
                )
            }

            composable(route = "profile") {
                actual.value = "profile"
                ProfileScreen(
                    viewModel = hiltViewModel<ProfileViewModel>(),
                    onEditPost = {id: Int -> navController.navigate("editPost/$id")}
                )
            }
            composable(
                route = "editPost/{id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.IntType
                    }
                )
            ) {
                val id = it.arguments?.getInt("id")
                EditPublicationScreen(
                    viewModel = hiltViewModel<EditPostViewModel>(),
                    goBack = {navController.popBackStack()},
                    modifier = Modifier,
                    idPost = id!!
                )
            }

        }
    }
}