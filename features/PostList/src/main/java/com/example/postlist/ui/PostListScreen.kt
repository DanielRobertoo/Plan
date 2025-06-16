package com.example.postlist.ui

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
import com.example.base.composables.NoDataPost
import com.example.chat.ui.base.AlertDialogOK
import com.example.chat.ui.base.AlertDialogYesNo
import com.example.chat.ui.base.AlertDialogYesNoPost
import com.example.domain.model.post
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
            viewModel.state.cerrarSesion -> {
                AlertDialogYesNo(
                    title = "Cerrar Sesion",
                    message = "¿Estás seguro de que quieres cerrar sesion?",
                    onAccept = {
                        viewModel.logOut()
                        goback()
                    },
                    onDismiss = {viewModel.reset()}
                )
            }
            viewModel.state.posts.isEmpty() -> {
                NoDataPost()
                Log.d("no data", "creado")
            }
            viewModel.state.requestExist -> AlertDialogOK(
                title = "ERROR",
                message = "Ya se ha mandado solicitud a esta publicacion",
                onDismiss = {viewModel.reset()}
            )
            viewModel.state.postDeleted -> AlertDialogOK(
                title = "ERROR",
                message = "No se ha podido acceder a la publicacion, es probable que el creador la haya eliminado",
                onDismiss = {
                    viewModel.reset()
                    viewModel.getPosts()
                }
            )
            viewModel.state.postToJoin != null -> {
                AlertDialogYesNoPost(
                    title = "Enviar Solicitud",
                    message = "",
                    onAccept = {
                        _post: post -> viewModel.SendRequest(_post)
                               },
                    onDismiss = { viewModel.reset() },
                    post = viewModel.state.postToJoin!!
                )
                Log.d("join", "creado")
            }
            viewModel.state.cerrarSesion -> AlertDialogYesNo(
                title = "Cerrar Sesion",
                message = "¿Estas seguro de que quieres cerrar sesion?",
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
                it.gym.lowercase(Locale.ROOT).contains("basic-fit")  -> PostItemBasicFit(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym, accionCorta = {
                    accion(id.toString())
                }, accionLarga = {})
                it.gym.lowercase(Locale.ROOT).contains("forus") -> PostItemForus(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym,
                    accion = {
                        accion(id.toString())
                    }, accionLarga = {})
                it.gym.lowercase(Locale.ROOT).contains("go fit") -> PostItemGofit(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym,accion = {
                    accion(id.toString())
                }, accionLarga = {})
                else -> PostItemDefault(user = it.post_creator_username, date = it.date, title = it.title, place = it.gym,accion = {
                    accion(id.toString())
                }, accionLarga = {})
            }
        }
    }
}

