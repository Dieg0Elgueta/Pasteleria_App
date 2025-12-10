package com.example.pasteleria_mil_sabores.ui.components

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pasteleria_mil_sabores.R
import com.example.pasteleria_mil_sabores.model.Producto

@Composable
fun ProductoItem(producto: Producto, onAgregarAlCarrito: (Producto) -> Unit = {}) {
    val context = LocalContext.current
    val imageResource = obtieneImagen(context, producto.imagen)


    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        border = BorderStroke(2.dp, Color(0xFFFFC0CB))
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFFFF5E1)
                ),
                modifier = Modifier.size(80.dp)
            ) {
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "Imagen de ${producto.nombre}",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = producto.nombre,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF8B4513)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = producto.categoria,
                    fontSize = 14.sp,
                    color = Color(0xFFB0BEC5)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$$${"%,d".format(producto.precio)} CLP",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFC0CB)
                )

                Button(
                    onClick = { onAgregarAlCarrito(producto) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8B4513)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.AddShoppingCart,
                        contentDescription = "Agregar al carrito"
                    )
                    Text("Agregar al carrito", modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
    }
}

private fun obtieneImagen(context: Context, imagen: String?): Int {
    val nombre = imagen?.replace(".jpg", "")?.replace(".png", "") ?: "torta_default"
    val resourceId = context.resources.getIdentifier(nombre, "drawable", context.packageName)

    return if (resourceId == 0) R.drawable.torta_default else resourceId
}