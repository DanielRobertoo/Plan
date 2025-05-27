package com.example.plan.ui.graph

import android.annotation.SuppressLint
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.chat.ui.ChatView.ChatScreen
import com.example.chat.ui.ChatView.ChatViewModel
import com.example.chat.ui.CreatePublicacion.CreatePublicationScreen
import com.example.chat.ui.CreatePublicacion.CreatePublicationViewModel
import com.example.login.ui.login.LoginScreen
import com.example.login.ui.login.LoginViewModel
import com.example.login.ui.register.RegisterScreen
import com.example.login.ui.register.RegisterViewModel
import com.example.plan.ui.graph.EntryAppGraph.ROUTE
import com.example.postlist.ui.PostListScreen
import com.example.postlist.ui.PostListViewModel
import com.example.validateemail.ui.ValidateEmailScreen
import com.example.validateemail.ui.ValidateEmailViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite

object EntryAppGraph{
    const val ROUTE = "app"
    fun login() = "$ROUTE/login"
    fun signUp() = "$ROUTE/signup"
    fun validate() = "$ROUTE/validate"
    fun postlist() = "$ROUTE/postlist"
    fun createpost() = "$ROUTE/createPost"
}

fun NavGraphBuilder.loginGraph(navController: NavController){
    navigation(startDestination = EntryAppGraph.login(), route = EntryAppGraph.ROUTE){
        login(navController)
        signUp(navController)
        validate(navController)
    }
}



fun NavGraphBuilder.appGraph(navController: NavController){
    navigation(startDestination = EntryAppGraph.login(), route = EntryAppGraph.ROUTE){
        postlist(navController)
        createpost(navController)
    }
}


fun NavGraphBuilder.login(navController: NavController){
    composable(route = EntryAppGraph.login()) {
        LoginScreen(
            viewModel = hiltViewModel<LoginViewModel>(),
            goToListAccount = { navController.navigate(EntryAppGraph.postlist()) },
            goToRegister = {navController.navigate(EntryAppGraph.signUp())},
            modifier = Modifier,
            email = "",
            goback = {},
            password = ""
        )
    }
}

fun NavGraphBuilder.signUp(navController: NavController){
    composable(route = EntryAppGraph.signUp()){
        RegisterScreen(
            viewModel = hiltViewModel<RegisterViewModel>(),
            modifier = Modifier,
            goToLogin = {navController.navigate(EntryAppGraph.login())},
            goValidate = { email: String, password: String ->
                navController.navigate("${EntryAppGraph.validate()}/$email/$password")
            }
        )
    }
}

fun NavGraphBuilder.validate(navController: NavController){
    composable(route = "${EntryAppGraph.validate()}/{email}/{password}",
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
                navController.navigate(EntryAppGraph.login())
            },
            goBack = { navController.popBackStack() },
            email = email!!,
            password = password!!
        )
    }
}

fun NavGraphBuilder.postlist(navController: NavController){
    composable(route = EntryAppGraph.postlist()) {
        PostListScreen(
            viewModel = hiltViewModel<PostListViewModel>(),
            goAdd = { navController.navigate(EntryAppGraph.createpost()) },
            goRequest = {},
        )
    }
}

fun NavGraphBuilder.createpost(navController: NavController){
    composable(route = EntryAppGraph.createpost()) {
        CreatePublicationScreen(
            viewModel = hiltViewModel<CreatePublicationViewModel>(),
            goBack = { navController.popBackStack() },
            modifier = Modifier
        )
    }
}

