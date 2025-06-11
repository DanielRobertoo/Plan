package com.example.base.PostGym

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.base.R


/**
 * Post item
 * Ejemplo de como se veria alguien que publica una actividad en un basic fit
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PostItemBasicFitPreview(){
    ElevatedCard(
        Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(10.dp)) {
        Box(contentAlignment = Alignment.Center){
            Column(Modifier.fillMaxSize()) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.basicfit),
                        contentDescription = "",
                    )
                }
                Row {
                    Column(Modifier.padding(10.dp)) {
                        Text("Rutina full body")
                        Text("15:00")
                        Text("La isla")
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = ""
                            )
                            Text("Usuario")
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostItemBasicFit(title: String, date: String, user:String, place:String, accionCorta: (String) -> Unit, accionLarga: () -> Unit) {
    ElevatedCard(Modifier
        .fillMaxWidth()
        .height(260.dp)
        .padding(10.dp)
        .combinedClickable(
            onClick = { accionCorta(user) },
            onLongClick = {accionLarga()}
        )) {
        Box(contentAlignment = Alignment.Center) {
            Column(Modifier.fillMaxSize()) {
                Row {
                    Image(
                        painter = painterResource(R.drawable.basicfit),
                        contentDescription = "",
                    )
                }
                Row {
                    Column(Modifier.padding(10.dp)) {
                        Text(title)
                        Text(date)
                        Text(place)
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(5.dp)) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = ""
                            )
                            Text(user)
                        }
                    }
                }
            }
        }
    }
}