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
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.chatlist.usecase.chatToShow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatListScreen(viewModel: ChatListViewModel, goChat: (Int) -> Unit) {
    LaunchedEffect(Unit) {
        viewModel.getChat()
    }
    when {
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
                modifier = Modifier.padding(),
                accion = { id: Int -> goChat(id) },
                viewModel = viewModel
            )
        }
    }

}


@Composable
fun ChatListContent(
    listChat: List<chatToShow>,
    modifier: Modifier,
    accion: (Int) -> Unit,
    viewModel: ChatListViewModel
) {
    LazyColumn(modifier = modifier) {
        items(listChat) {
            val idChat = it.idChat
            ChatListItem(
                userName = it.username,
                onClick = {
                    accion(idChat!!)
                },
                lastMessage = it.lastMessage,
                time = it.time,
                id = it.idChat!!
            )
            HorizontalDivider()

        }
    }
}









