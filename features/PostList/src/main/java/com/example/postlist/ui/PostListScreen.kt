package com.example.postlist.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Button
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.unit.dp
import com.example.base.PostGym.PostItemBasicFit
import com.example.base.PostGym.PostItemDefault
import com.example.base.PostGym.PostItemForus
import com.example.base.PostGym.PostItemGofit
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.chat.ui.base.AlertDialogYesNo
import com.example.chat.ui.base.AlertDialogYesNoPost
import com.example.domain.model.post
import com.example.postlist.R
import com.example.postlist.usecase.PostListState
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(viewModel: PostListViewModel, goRequest: (post) -> Unit, goAdd: () -> Unit, goback: () -> Unit){
    LaunchedEffect(Unit) {
        viewModel.getPosts()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
        floatingActionButton = {
            FloatingActionButton(onClick = {goAdd() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Añadir Avería"
                )
            }

        },topBar = {
            CenterAlignedTopAppBar(
                title = { Text("") },
                actions = { IconButton(onClick = { viewModel.setLogOut() }, content = { Icon( imageVector = Icons.Filled.ExitToApp, contentDescription = "")}) },
            )
        }
    ) { padding ->
        when{
            viewModel.state.loading -> {
                LoadingUi()
                Log.d("Loading", "creado")
            }
            viewModel.state.noData -> {
                NoDataScreen()
                Log.d("no data", "creado")
            }
            viewModel.state.postToJoin != null -> {
                AlertDialogYesNoPost(
                    title = "Enviar Solicitud",
                    message = "",
                    onAccept = {_post: post -> viewModel.SendRequest(_post) },
                    onDismiss = { viewModel.reset() },
                    post = viewModel.state.postToJoin!!
                )
                Log.d("join", "creado")
            }
            viewModel.state.cerrarSesion -> AlertDialogYesNo(
                title = "Cerrar Sesion",
                message = "",
                onAccept = {
                    viewModel.logOut()
                    goback()
                           },
                onDismiss = { viewModel.reset() },
            )
            viewModel.state.posts.isNotEmpty() -> {
                PostListContent(
                    listPost = viewModel.state.posts,
                    modifier = Modifier.padding(padding),
                    accion = { id:String -> viewModel.askRequest(id) }
                )
                Log.d("list", "creado")
            }
        }
    }
}





@Composable
fun PostListContent(listPost: List<post>, modifier: Modifier, accion: (String) -> Unit){
    LazyColumn(modifier = modifier) {
        Log.d("lista gimnasios", listPost.toString())
        items(listPost) {
            var id = it.id
            when{
                it.gym.lowercase(Locale.ROOT).contains("basic-fit")  -> PostItemBasicFit(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym, accion = {
                    accion(id.toString())
                })
                it.gym.lowercase(Locale.ROOT).contains("forus") -> PostItemForus(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym,
                    accion = {
                        accion(id.toString())
                    })
                it.gym.lowercase(Locale.ROOT).contains("GO fit") -> PostItemGofit(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym,accion = {
                    accion(id.toString())
                })
                else -> PostItemDefault(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym,accion = {
                    accion(id.toString())
                })
            }
        }
    }
}

