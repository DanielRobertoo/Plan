package com.example.plan.ui.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.postlist.ui.PostListScreen
import com.example.postlist.ui.PostListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun PlanScreen(){
    val navController = rememberNavController()

    NavHost(startDestination = "list", navController = navController){
        composable(route = "list") {
            PostListScreen(
                viewModel = hiltViewModel<PostListViewModel>(),
                goAdd = {navController.navigate("addPost")},
                goRequest = {}
            )
        }
        composable(route = "add") {

        }

    }
}