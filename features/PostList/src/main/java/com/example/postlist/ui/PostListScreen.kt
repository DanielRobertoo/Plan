package com.example.postlist.ui

import android.annotation.SuppressLint
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.airport.ui.base.composables.BaseTopAppBar
import com.example.airport.ui.base.composables.BaseTopAppBarState
import com.example.base.PostGym.PostItemBasicFit
import com.example.base.PostGym.PostItemDefault
import com.example.base.PostGym.PostItemForus
import com.example.base.PostGym.PostItemGofit
import com.example.base.composables.LoadingUi
import com.example.base.composables.NoDataScreen
import com.example.chat.ui.base.AlertDialogYesNoPost
import com.example.domain.model.post
import com.example.postlist.R
import com.example.postlist.usecase.PostListState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostListScreen(viewModel: PostListViewModel, goRequest: (post) -> Unit, goAdd: () -> Unit, modifier: Modifier){
    LaunchedEffect(Unit) {
        viewModel.getPosts()
    }
    Scaffold(
        topBar = {
            BaseTopAppBar(
                appBarState = BaseTopAppBarState(
                    title = "Aeropuertos",
                    upAction = { },
                    iconUpAction = null,
                    actions = listOf()
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {goAdd() }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Añadir Avería"
                )
            }

        }
    ) { padding ->
        when{
            viewModel.state.loading -> LoadingUi()
            viewModel.state.noData -> NoDataScreen()
            viewModel.state.postToJoin != null -> AlertDialogYesNoPost(
                title = "ALERTA",
                message = "Seguro quieres borrar el aeropuerto?",
                onAccept = {_post: post -> viewModel.SendRequest(_post) },
                onDismiss = { viewModel.reset() },
                post = viewModel.state.postToJoin!!
            )
            viewModel.state.posts.isNotEmpty() -> PostListContent(
                state = viewModel.state,
                findUser = { id:String -> viewModel.findUserById(id) }
            )
        }

    }
}


@Composable
fun PostListContent(state: PostListState, findUser: (String) -> String){
    LazyColumn {
        items(state.posts) {
            when{
                it.gym == "basicFit" -> PostItemBasicFit(user = findUser(it.post_creator_id), date = it.date, title = it.title, place = it.gym)
                it.gym == "forus" -> PostItemForus(user = findUser(it.post_creator_id), date = it.date, title = it.title, place = it.gym)
                it.gym == "gofit" -> PostItemGofit(user = findUser(it.post_creator_id), date = it.date, title = it.title, place = it.gym)
                else -> PostItemDefault(user = findUser(it.post_creator_id), date = it.date, title = it.title, place = it.gym)
            }
        }
    }
}

