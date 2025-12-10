package com.example.pasteleria_mil_sabores.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BarraBusqueda(
    valor: String,
    onValorCambio: (String) -> Unit,
    placeholder: String = "Buscar productos..."
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambio,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        placeholder = {
            Text(
                text = placeholder,
                color = Color(0xFFB0BEC5)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Buscar",
                tint = Color(0xFF8B4513)
            )
        },
        trailingIcon = {
            if (valor.isNotEmpty()) {
                IconButton(onClick = { onValorCambio("") }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Limpiar",
                        tint = Color(0xFFB0BEC5)
                    )
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFC0CB),
            unfocusedBorderColor = Color(0xFFFFB6C1),
            focusedLeadingIconColor = Color(0xFF8B4513),
            unfocusedLeadingIconColor = Color(0xFF5D4037),
            cursorColor = Color(0xFF8B4513),
            focusedTextColor = Color(0xFF5D4037),
            unfocusedTextColor = Color(0xFF5D4037),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        ),
        singleLine = true
    )
}