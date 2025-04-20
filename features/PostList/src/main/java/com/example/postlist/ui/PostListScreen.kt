package com.example.postlist.ui

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.postlist.R

@Composable
fun PostListContent(){

}

/**
 * Post item
 * Ejemplo de como se veria alguien que publica una actividad en un basic fit
 */
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PostItem(){
    ElevatedCard(Modifier.fillMaxWidth().height(230.dp).padding(10.dp)) {
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
                        Text("TITULO")
                        Text("HORA")
                        Text("USER")
                    }
                }
            }
        }
    }

}