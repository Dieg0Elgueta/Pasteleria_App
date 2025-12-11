package com.example.pasteleria_mil_sabores.ui.screen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun HomeScreen_muestraTitulo(){
        composeTestRule.setContent {
            HomeScreen(navController = rememberNavController(), 1)
        }
        composeTestRule.onNodeWithText("Pastelería Mil Sabores").assertIsDisplayed()
    }
}