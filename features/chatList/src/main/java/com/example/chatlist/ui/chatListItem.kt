package com.example.chatlist.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Badge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ChatListItem(
    //profilePicture: Int,
    userName: String,
    lastMessage: String,
    time: String,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = { })
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
//        Image(
//            painter = painterResource(id = profilePicture),
//            contentDescription = "Profile Picture",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(48.dp)
//                .background(Color.Gray, shape = CircleShape)
//                .padding(2.dp)
//        )

        Spacer(modifier = Modifier.width(12.dp))

        // Chat content
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = userName, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(
                text = lastMessage,
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1
            )
        }

        // Time and unread badge
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(text = time, fontSize = 12.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ChatListItemPreview() {
    ChatListItem(

        userName = "Juan Pérez",
        lastMessage = "¿Vienes mañana?",
        time = "12:45",
    )
}