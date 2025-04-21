package com.example.base.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SmallSpace() = Spacer(modifier = Modifier.size(Separations.Small))

@Composable
fun MediumSpace() = Spacer(modifier = Modifier.size(Separations.Medium))

object Separations {
    val Small = 8.dp
    val Medium = 16.dp
}