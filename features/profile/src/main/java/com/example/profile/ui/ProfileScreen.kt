package com.example.profile.ui

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
fun ProfileScreen(viewModel: ProfileViewModel, onEdit: (id: Int) -> Unit) {

}


data class ProfileEvents(
    val EditProfile: (Int) -> Unit,
    val CheckPost: (String) -> Unit
)

@Composable
fun ProfileContent(state: ProfileState, events: ProfileEvents, onEdit:(id:Int) -> Unit) {
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
            Text("${state.user!!.user_name}")
            ElevatedButton(
                onClick = {events.EditProfile(state.user!!.id)},
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
        HorizontalDivider()
        LazyColumn {
            items(state.posts){
                val id = it.id
                when{

                    it.gym == "basicFit" -> PostItemBasicFit(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
                        onEdit(id)
                    })
                    it.gym == "forus" -> PostItemForus(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
                        onEdit(id)
                    })
                    it.gym == "gofit" -> PostItemGofit(user = "${state.user!!.user_name}", date = it.date, title = it.title, place = it.gym, accion = {
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