package com.example.base.PostRequest

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.request

@Composable
fun PostRequestItemForus(request: request, onAccept: (Int, Int, Int) -> Unit, onRefuse: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(com.example.base.R.drawable.forus),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Título: ${request.title}")
                Text(text = "Fecha: ${request.date}")
                Text(text = "Con: ${request.username_request}")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = {onAccept(request.id, request.id_guest, request.id_owner)},
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                    containerColor = Color.Transparent
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                }
                FloatingActionButton(
                    onClick = {},
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                    containerColor = Color.Transparent
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PostRequestItemForusPreview() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(com.example.base.R.drawable.forus),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = "Título: Rutina de pierna")
                Text(text = "Fecha: 03/06")
                Text(text = "Con: Roberto")
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    onClick = {},
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                    containerColor = Color.Transparent
                ) {
                    Icon(imageVector = Icons.Filled.Check, contentDescription = "")
                }
                FloatingActionButton(
                    onClick = {},
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
                    containerColor = Color.Transparent
                ) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "")
                }
            }
        }
    }
}