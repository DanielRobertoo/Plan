package com.example.chatlist.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.domain.model.chat
import com.example.domain.model.post

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(viewModel: chatListViewModel, goChat: (Int) -> Unit){
    LaunchedEffect(Unit) {
        viewModel.getPosts()
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
                ChatListContent(
                    listChat = viewModel.state.chats,
                    modifier = Modifier.padding(padding),
                    accion = {id: Int -> goChat(id)},
                    viewModel = viewModel
                )
            }
        }
    }
}





@Composable
fun ChatListContent(listChat: List<chat>, modifier: Modifier, accion: (Int) -> Unit, viewModel: chatListViewModel){
    LazyColumn(modifier = modifier) {
        items(listChat) {
            if(it.user1_id == viewModel.idUser){
                ChatListItem(
                    userName = viewModel.getNameSender(it.user2_id),
                    onClick = {accion(it.id)},
                    lastMessage = viewModel.getLastMessage(it.user2_id),
                    time = it.created_at
                )
            }
            if (it.user2_id == viewModel.idUser) {
                ChatListItem(
                    userName = viewModel.getNameSender(it.user1_id),
                    onClick = {accion(it.id)},
                    lastMessage = viewModel.getLastMessage(it.user1_id),
                    time = it.created_at
                )
            }
        }
    }
}









