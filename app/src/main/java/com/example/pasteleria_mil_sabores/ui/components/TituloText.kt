package com.example.pasteleria_mil_sabores.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TituloText(titulo: String) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.headlineLarge.copy(
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF8B4513),
            letterSpacing = 1.2.sp
        ),
        textAlign = TextAlign.Center
    )
}