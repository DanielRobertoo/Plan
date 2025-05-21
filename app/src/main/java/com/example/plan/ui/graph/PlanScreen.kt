package com.example.plan.ui.graph

import androidx.compose.runtime.Composable
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
fun PlanScreen(modifier: Modifier, navController: NavHostController) {

    NavHost(startDestination = "login", navController = navController) {
        composable(route = "list") {
            PostListScreen(
                viewModel = hiltViewModel<PostListViewModel>(),
                goAdd = { navController.navigate("add") },
                goRequest = {},
            )
        }
        composable(route = "add") {
            CreatePublicationScreen(
                viewModel = hiltViewModel<CreatePublicationViewModel>(),
                goBack = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = "chat") {
            ChatScreen(
                viewModel = hiltViewModel<ChatViewModel>(),
                goBack = { navController.popBackStack() },
                modifier = modifier
            )
        }
        composable(route = "login") {
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
            RegisterScreen(
                viewModel = hiltViewModel<RegisterViewModel>(),
                goToLogin = { navController.popBackStack() },
                goValidate = { email: String, password: String ->
                    navController.navigate("validateEmail/$email/$password")
                },
                goBack = {navController.popBackStack()},
                modifier = Modifier
            )
        }

        composable(
            route = "validateEmail/{email}/{password}",
            arguments = listOf(
                navArgument("email") {
                    type = NavType.StringType
                },
                navArgument("password") {
                    type = NavType.StringType
                }
            )
        ) { argumento ->
            val email = argumento.arguments?.getString("email")
            val password = argumento.arguments?.getString("password")
            ValidateEmailScreen(
                viewModel = hiltViewModel<ValidateEmailViewModel>(),
                goLogin = {
                    navController.navigate("login")
                          },
                goBack = { navController.popBackStack() },
                email = email!!,
                password = password!!
            )
        }

    }
}