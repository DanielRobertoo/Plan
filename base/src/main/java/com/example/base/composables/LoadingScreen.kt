package com.example.base.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
private fun LoadingUiPreview() {
    LoadingUi()
}

@Composable
fun LoadingUi() {
    Box(modifier = Modifier.fillMaxSize().testTag("loadingIndicator"), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            MediumSpace()
            Text(text = "Cargando")
        }
    }
}
@Composable
fun LoadingUi2() {
    Box(modifier = Modifier.fillMaxSize().testTag("loadingIndicator").padding(10.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator()
            MediumSpace()
            Text(text = "Cargando\nSi tarda mas de 5 segundos vuelva a la \npantalla de registro y compruebe que el correo fue bien insertado")
        }
    }
}
