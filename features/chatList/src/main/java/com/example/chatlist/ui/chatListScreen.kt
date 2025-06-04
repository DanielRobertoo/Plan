package com.example.chatlist.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.chatlist.usecase.chatToShow
import com.example.domain.model.chat
import com.example.domain.model.post

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(viewModel: ChatListViewModel){
    LaunchedEffect(Unit) {
        viewModel.getChat()
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
            viewModel.state.listChatShow.isNotEmpty() -> {
                ChatListContent(
                    listChat = viewModel.state.listChatShow,
                    modifier = Modifier.padding(padding),
                    accion = {/*id: Int -> goChat(id)*/},
                    viewModel = viewModel
                )
            }
        }
    }
}





@Composable
fun ChatListContent(listChat: List<chatToShow>, modifier: Modifier, accion: (Int) -> Unit, viewModel: ChatListViewModel){
    LazyColumn(modifier = modifier) {
        items(listChat) {
                ChatListItem(
                    userName = it.username,
                    onClick = {accion(it.idChat!!)},
                    lastMessage = it.lastMessage,
                    time = it.time
                )
            HorizontalDivider()

        }
    }
}









