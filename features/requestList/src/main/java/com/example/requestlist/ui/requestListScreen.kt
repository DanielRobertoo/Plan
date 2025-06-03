package com.example.requestlist.ui

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.domain.model.chat
import com.example.domain.model.request
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FloatingActionButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.base.PostGym.PostItemBasicFit
import com.example.base.PostGym.PostItemDefault
import com.example.base.PostGym.PostItemForus
import com.example.base.PostGym.PostItemGofit
import com.example.base.PostRequest.PostRequestItemBasicFit
import com.example.base.PostRequest.PostRequestItemDefault
import com.example.base.PostRequest.PostRequestItemForus
import com.example.base.PostRequest.PostRequestItemGoFit
import com.example.requestlist.R
import java.util.Locale

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
                RequestListContent(
                    listChat = viewModel.state.requests,
                    modifier = Modifier.padding(padding),
                    onAccept = { id:Int -> viewModel.onAccept(id)},
                    onRefuse = { id:Int -> viewModel.onRefuse(id)}
                )
            }
        }
    }
}





@Composable
fun RequestListContent(listChat: List<request>, modifier: Modifier, onAccept: (Int) -> Unit, onRefuse: (Int) -> Unit ){
    LazyColumn(modifier = modifier) {
        items(listChat) {
            when{
                it.gym.lowercase(Locale.ROOT).contains("basic-fit")  -> PostRequestItemBasicFit(username = it.username_request, date = it.date, title = it.title, onAccept = onAccept, onRefuse = onRefuse, idRequest = it.id)
                it.gym.lowercase(Locale.ROOT).contains("forus") -> PostRequestItemForus(username = it.username_request, date = it.date, title = it.title, onAccept = onAccept, onRefuse = onRefuse, idRequest = it.id)
                it.gym.lowercase(Locale.ROOT).contains("GO fit") -> PostRequestItemGoFit(username = it.username_request, date = it.date, title = it.title, onAccept = onAccept, onRefuse = onRefuse, idRequest = it.id)
                else -> PostRequestItemDefault(username = it.username_request, date = it.date, title = it.title, onAccept = onAccept, onRefuse = onRefuse, idRequest = it.id)
            }
        }
    }
}




