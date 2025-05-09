package com.example.plan.ui.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun PlanScreen(modifier: Modifier) {
    val navController = rememberNavController()

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
                        navController.popBackStack()
                    }
                },
                goback = { navController.popBackStack() }
            )
        }

        composable(route = "signUp") {
            RegisterScreen(
                viewModel = hiltViewModel<RegisterViewModel>(),
                goToLogin = { navController.popBackStack() },
                modifier = Modifier
            )
        }

    }
}