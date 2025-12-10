package com.example.pasteleria_mil_sabores.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FiltroCategorias(
    categorias: List<String>,
    categoriaSeleccionada: String?,
    onCategoriaSeleccionada: (String?) -> Unit
) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            ChipCategoria(
                texto = "Todos",
                seleccionado = categoriaSeleccionada == null,
                onClick = { onCategoriaSeleccionada(null) }
            )
        }
        items(categorias) { categoria ->
            ChipCategoria(
                texto = categoria,
                seleccionado = categoriaSeleccionada == categoria,
                onClick = { onCategoriaSeleccionada(categoria) }
            )
        }
    }
}

@Composable
fun ChipCategoria(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (seleccionado) Color(0xFFFFC0CB)
                else Color.White
            )
            .border(
                width = 2.dp,
                color = if (seleccionado) Color(0xFF8B4513)
                else Color(0xFFFFB6C1), // Rosa más claro
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = if (seleccionado) Color(0xFF8B4513)
            else Color(0xFF5D4037),
            fontSize = 14.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
        )
    }
}