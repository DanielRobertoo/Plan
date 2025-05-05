package com.example.base.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SpaceBajo(){
    Spacer(modifier = Modifier.padding(9.dp))
}

@Composable
fun SpaceMedio(){
    Spacer(modifier = Modifier.padding(20.dp))
}

@Composable
fun SpaceAlto(){
    Spacer(modifier = Modifier.padding(60.dp))
}