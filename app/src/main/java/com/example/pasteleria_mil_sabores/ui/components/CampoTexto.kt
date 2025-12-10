package com.example.pasteleria_mil_sabores.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CampoTexto(
    valor: String,
    onValorCambio: (String) -> Unit,
    etiqueta: String,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorCambio,
        label = {
            Text(
                text = etiqueta,
                color = Color(0xFF8B4513)
            )
        },
        visualTransformation = visualTransformation,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFFFC0CB),
            unfocusedBorderColor = Color(0xFFFFB6C1),
            focusedLabelColor = Color(0xFF8B4513),
            unfocusedLabelColor = Color(0xFF5D4037),
            cursorColor = Color(0xFF8B4513),
            focusedTextColor = Color(0xFF5D4037),
            unfocusedTextColor = Color(0xFF5D4037)
        )
    )
}