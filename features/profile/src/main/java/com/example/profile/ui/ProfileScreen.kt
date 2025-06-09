package com.example.profile.ui

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.base.PostGym.PostItemBasicFit
import com.example.base.PostGym.PostItemBasicFitPreview
import com.example.base.PostGym.PostItemDefault
import com.example.base.PostGym.PostItemForus
import com.example.base.PostGym.PostItemGofit
import com.example.base.PostGym.PostItemGofitPreview
import com.example.base.composables.LoadingUi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfileContentPreview() {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                Modifier.size(80.dp)
            )
            Text("Roberto Daniel Caraivan")
            ElevatedButton(
                onClick = {},
                elevation = ButtonDefaults.buttonElevation(0.dp),
                colors = ButtonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent

                )
            ) {
                Text("Editar Perfil")
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Publicaciones")
        }
        Spacer(Modifier.padding(5.dp))
        HorizontalDivider()
        Column {
            PostItemBasicFitPreview()
            PostItemGofitPreview()

        }
    }
}

@Composable
fun ProfileScreen(viewModel: ProfileViewModel, onEditPost: (id: Int) -> Unit ) {
    LaunchedEffect(Unit) {
        viewModel.getData()
    }
    when{
        viewModel.state.loading -> LoadingUi()
        else -> ProfileContent(
            onEdit = onEditPost,
            state = viewModel.state
        )
    }

}


data class ProfileEvents(
    val EditProfile: (Int) -> Unit,
    val CheckPost: (String) -> Unit
)

@Composable
fun ProfileContent(state: ProfileState, onEdit:(id:Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "",
                Modifier.size(80.dp)
            )
            Text("${
                if(state.user != null){
                    state.user.user_name
                }
                else{
                    ""
                }
            }")
            ElevatedButton(
                onClick = {/*events.EditProfile(state.user!!.id)*/},
                elevation = ButtonDefaults.buttonElevation(0.dp),
                colors = ButtonColors(
                    contentColor = Color.Black,
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    disabledContentColor = Color.Transparent

                )
            ) {
                Text("Editar Perfil")
            }
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text("Publicaciones Activas")
        }
        HorizontalDivider()
        LazyColumn {
            items(state.posts){
                Log.d("post", it.toString())
                val id = it.id
                if (!LocalDate.parse(it.date, DateTimeFormatter.ofPattern("dd/MM/yyyy")).isBefore(LocalDate.now())){
                    when{
                        it.gym.lowercase(Locale.ROOT).contains("basic-fit") -> PostItemBasicFit(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
                            onEdit(id)
                        })
                        it.gym.lowercase(Locale.ROOT).contains("forus") -> PostItemForus(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
                            onEdit(id)
                        })
                        it.gym.lowercase(Locale.ROOT).contains("go fit") -> PostItemGofit(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
                            onEdit(id)
                        })
                        else -> PostItemDefault(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
                            onEdit(id)
                        })
                    }
                }

            }
        }
    }
}