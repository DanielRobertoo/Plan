package com.example.requestlist.ui

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
import com.example.domain.model.request

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(viewModel: requestListViewModel, goChat: (Int) -> Unit){
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
            viewModel.state.requests.isNotEmpty() -> {
//                RequestListContent(
//                    listChat = viewModel.state.chats,
//                    modifier = Modifier.padding(padding),
//                    accion = {id: Int -> goChat(id)},
//                    viewModel = viewModel
//                )
            }
        }
    }
}





@Composable
fun RequestListContent(listChat: List<request>, modifier: Modifier, onAccept: (Int) -> Unit, onRefuse: (Int) -> Unit ,viewModel: requestListViewModel){
    LazyColumn(modifier = modifier) {
        items(listChat) {

        }
    }
}


@Composable
fun RequestItem(){

}