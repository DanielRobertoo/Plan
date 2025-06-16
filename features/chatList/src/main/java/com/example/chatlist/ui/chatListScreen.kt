package com.example.chatlist.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataChats
import com.example.chatlist.usecase.chatToShow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(viewModel: ChatListViewModel, goChat: (Int, String) -> Unit) {
    LaunchedEffect(Unit) {

        viewModel.getChat()
    }
    when {
        viewModel.state.loading -> {
            LoadingUi()
            Log.d("Loading", "creado")
        }

        viewModel.state.chats.isEmpty() -> {
            NoDataChats()
            Log.d("no data", "creado")
        }

        viewModel.state.listChatShow.isNotEmpty() -> {
            ChatListContent(
                listChat = viewModel.state.listChatShow,
                modifier = Modifier.padding(10.dp),
                accion = { id: Int, name: String -> goChat(id,name) },
                viewModel = viewModel
            )
        }
    }

}


@Composable
fun ChatListContent(
    listChat: List<chatToShow>,
    modifier: Modifier,
    accion: (Int,String) -> Unit,
    viewModel: ChatListViewModel
) {
    LazyColumn(modifier = modifier) {
        items(listChat) {
            val idChat = it.idChat
            val name = it.username
            ChatListItem(
                userName = it.username,
                onClick = {
                    accion(idChat!!,name )
                },
                lastMessage = it.lastMessage,
                time = it.time,
                id = it.idChat!!
            )
            HorizontalDivider()

        }
    }
}









