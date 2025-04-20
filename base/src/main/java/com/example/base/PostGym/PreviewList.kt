package com.example.base.PostGym

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun prueba(){
    Column(Modifier.scrollable(
        enabled = true, orientation = Orientation.Vertical, state = rememberScrollState()
    )
    ) {
        PostItemGofit()
        PostItemBasicFit()
        PostItemForus()
        PostItemDefault()
    }

}