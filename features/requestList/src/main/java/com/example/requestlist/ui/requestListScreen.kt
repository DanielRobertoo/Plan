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
import com.example.domain.model.chat
import com.example.domain.model.request
import com.example.base.PostRequest.PostRequestItemBasicFit
import com.example.base.PostRequest.PostRequestItemDefault
import com.example.base.PostRequest.PostRequestItemForus
import com.example.base.PostRequest.PostRequestItemGoFit
import com.example.base.composables.NoDataRequest
import com.example.requestlist.R
import java.util.Locale


@Composable
fun RequestListScreen(viewModel: RequestListViewModel){
    LaunchedEffect(Unit) {
        viewModel.getRequest()
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
            viewModel.state.requests.isEmpty() -> NoDataRequest()
            viewModel.state.requests.isNotEmpty() -> {
                RequestListContent(
                    listChat = viewModel.state.requests,
                    modifier = Modifier.padding(padding),
                    onAccept = { idRequest:Int, idOwner: Int, idGuest: Int -> viewModel.onAccept(idRequest, idOwner, idGuest)},
                    onRefuse = { id:Int -> viewModel.onRefuse(id)}
                )
            }
        }
    }
}





@Composable
fun RequestListContent(listChat: List<request>, modifier: Modifier, onAccept: (Int, Int,Int) -> Unit, onRefuse: (Int) -> Unit ){
    LazyColumn(modifier = modifier) {
        items(listChat) {
            when{
                it.gym.lowercase(Locale.ROOT).contains("basic-fit")  -> PostRequestItemBasicFit(request = it, onAccept = onAccept, onRefuse = onRefuse)
                it.gym.lowercase(Locale.ROOT).contains("forus") -> PostRequestItemForus(request = it, onAccept = onAccept, onRefuse = onRefuse)
                it.gym.lowercase(Locale.ROOT).contains("GO fit") -> PostRequestItemGoFit(request = it, onAccept = onAccept, onRefuse = onRefuse)
                else -> PostRequestItemDefault(request = it, onAccept = onAccept, onRefuse = onRefuse)
            }
        }
    }
}




