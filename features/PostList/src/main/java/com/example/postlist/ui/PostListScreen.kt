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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.base.PostGym.PostItemBasicFit
import com.example.base.PostGym.PostItemDefault
import com.example.base.PostGym.PostItemForus
import com.example.base.PostGym.PostItemGofit
import com.example.postlist.R
import com.example.postlist.usecase.PostListState

@Composable
fun PostListContent(state: PostListState){
    LazyColumn {
        items(state.posts) {
            when{
                it.gym == "basicFit" -> PostItemBasicFit()
                it.gym == "forus" -> PostItemForus()
                it.gym == "gofit" -> PostItemGofit()
                else -> PostItemDefault()
            }
        }
    }
}

