package com.example.plan

import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.base.composables.NoDataChats
import com.example.base.composables.OfflineUi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OfflineScreenMessageText {
    //1. Jetpack Compose: Se usa createAndroidComposeRule para obtener una
    //actividad donde dibujar/renderizar un compose

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun `muestra mensaje offline true`(){
        composeTestRule.activity.setContent {
            MaterialTheme{
                OfflineUi()
            }
        }
        val offlineMessage = "No hay internet"
        composeTestRule.onNodeWithText(offlineMessage).isDisplayed()
    }
    @Test
    fun `muestra mensaje no data true`(){
        composeTestRule.activity.setContent {
            MaterialTheme{
                NoDataChats()
            }
        }
        val offlineMessage = "No hay chats aun."
        composeTestRule.onNodeWithText(offlineMessage).isDisplayed()
    }
}