package com.example.pasteleria_mil_sabores.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleria_mil_sabores.model.CartItem
import com.example.pasteleria_mil_sabores.viewmodel.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController, usuarioId: Int) {
    val cartViewModel: CartViewModel = viewModel()
    val carritoItems by cartViewModel.carritoItems.collectAsState()
    var total by remember { mutableStateOf(0) }

    LaunchedEffect(usuarioId) {
        cartViewModel.cargarCarrito(usuarioId)
        cartViewModel.obtenerTotalCarrito(usuarioId).collect {
            total = it
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Carrito",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color(0xFF8B4513)
                )
            )
        },
        bottomBar = {
            if (carritoItems.isNotEmpty()) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    tonalElevation = 8.dp,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "Total: $${total}",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF8B4513)
                        )
                        Button(
                            onClick = {
                                navController.navigate("home/$usuarioId")
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF8B4513)
                            )
                        ) {
                            Text("Volver a Comprar")
                        }
                    }
                }
            }
        }
    ) { padding ->
        if (carritoItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Carrito vacío",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Text(
                        "Tu carrito está vacío",
                        fontSize = 18.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                    Button(
                        onClick = {
                            navController.navigate("home/$usuarioId")
                        },
                        modifier = Modifier.padding(top = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF8B4513)
                        )
                    ) {
                        Text("Seguir Comprando")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(carritoItems) { item ->
                    CartItemCard(
                        item = item,
                        onEliminar = { cartViewModel.eliminarDelCarrito(usuarioId, it) },
                        onCantidadChange = { nuevaCantidad ->
                            cartViewModel.actualizarCantidad(usuarioId, item, nuevaCantidad)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onEliminar: (CartItem) -> Unit,
    onCantidadChange: (Int) -> Unit
) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Producto ID: ${item.productoId}",
                    fontWeight = FontWeight.Bold
                )
                Text("Cantidad: ${item.cantidad}")
                Text("Precio: $${item.precioUnitario}")
                Text("Subtotal: $${item.cantidad * item.precioUnitario}")
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(
                    onClick = {
                        if (item.cantidad > 1) {
                            onCantidadChange(item.cantidad - 1)
                        } else {
                            onEliminar(item)
                        }
                    }
                ) {
                    Text("-", fontSize = 20.sp)
                }

                Text(
                    text = "${item.cantidad}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                IconButton(
                    onClick = { onCantidadChange(item.cantidad + 1) }
                ) {
                    Text("+", fontSize = 20.sp)
                }

                IconButton(
                    onClick = { onEliminar(item) }
                ) {
                    Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}