package com.example.chatlist.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.base.PostGym.PostItemBasicFit
import com.example.base.PostGym.PostItemDefault
import com.example.base.PostGym.PostItemForus
import com.example.base.PostGym.PostItemGofit
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.chat.ui.base.AlertDialogYesNo
import com.example.chat.ui.base.AlertDialogYesNoPost
import com.example.chatlist.usecase.ChatListState
import com.example.domain.model.chat
import com.example.domain.model.post
import com.example.postlist.ui.PostListViewModel
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(viewModel: chatListViewModel, goRequest: (post) -> Unit, goAdd: () -> Unit, goback: () -> Unit){
    LaunchedEffect(Unit) {
        //viewModel.getPosts()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
    ) {  padding ->
        when{
            viewModel.state.loading -> {
                LoadingUi()
                Log.d("Loading", "creado")
            }
            viewModel.state.noData -> {
                NoDataScreen()
                Log.d("no data", "creado")
            }
            viewModel.state.chats.isNotEmpty() -> {


            }
        }
    }
}





@Composable
fun ChatListContent(listPost: List<chat>, modifier: Modifier, accion: (String) -> Unit, idUser: Int){
    LazyColumn(modifier = modifier) {
        Log.d("lista gimnasios", listPost.toString())
        items(listPost) {
            var id = it.id
            if(it.user1_id == id || it.user2_id == id){

            }
        }
    }
}







