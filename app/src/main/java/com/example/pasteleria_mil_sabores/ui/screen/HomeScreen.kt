package com.example.pasteleria_mil_sabores.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.pasteleria_mil_sabores.model.Producto
import com.example.pasteleria_mil_sabores.ui.components.BarraBusqueda
import com.example.pasteleria_mil_sabores.ui.components.BotonLogin
import com.example.pasteleria_mil_sabores.ui.components.FiltroCategorias
import com.example.pasteleria_mil_sabores.ui.components.ProductoItem
import com.example.pasteleria_mil_sabores.viewmodel.CartViewModel
import com.example.pasteleria_mil_sabores.viewmodel.ProductoViewModel

@Composable
fun HomeScreen(navController: NavController, usuarioId: Int) {
    val viewModel: ProductoViewModel = viewModel()
    val productos by viewModel.productos.collectAsState()
    val categorias by viewModel.categorias.collectAsState()

    var busqueda by remember { mutableStateOf("") }
    var categoriaSeleccionada by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    val cartViewModel: CartViewModel = viewModel()

    LaunchedEffect(Unit) {
        viewModel.cargarCategorias()
        viewModel.cargarProductosDisponibles()
        isLoading = false
    }

    LaunchedEffect(busqueda) {
        if (busqueda.isNotEmpty()) {
            viewModel.buscarProductos(busqueda)
            categoriaSeleccionada = null
        } else if (categoriaSeleccionada != null) {
            viewModel.cargarProductosPorCategoria(categoriaSeleccionada!!)
        } else {
            viewModel.cargarProductosDisponibles()
        }
    }

    LaunchedEffect(categoriaSeleccionada) {
        if (busqueda.isEmpty()) {
            if (categoriaSeleccionada != null) {
                viewModel.cargarProductosPorCategoria(categoriaSeleccionada!!)
            } else {
                viewModel.cargarProductosDisponibles()
            }
        }
    }

    HomeScreenContent(
        navController = navController,
        usuarioId = usuarioId,
        productos = productos,
        categorias = categorias,
        busqueda = busqueda,
        onBusquedaCambio = { busqueda = it },
        categoriaSeleccionada = categoriaSeleccionada,
        onCategoriaSeleccionada = { categoriaSeleccionada = it },
        isLoading = isLoading,
        cartViewModel = cartViewModel

    )
}

@Composable
fun HomeScreenContent(
    navController: NavController,
    usuarioId: Int,
    productos: List<Producto>,
    categorias: List<String>,
    busqueda: String,
    onBusquedaCambio: (String) -> Unit,
    categoriaSeleccionada: String?,
    onCategoriaSeleccionada: (String?) -> Unit,
    isLoading: Boolean,
    cartViewModel: CartViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(vertical = 24.dp, horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎂 Pastelería Mil Sabores 🎂",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF8B4513),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Nuestros Deliciosos Productos",
                        fontSize = 16.sp,
                        color = Color(0xFFB0BEC5),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    BotonLogin(
                        texto = "Mi Perfil",
                        onClickAccion = {
                            navController.navigate("perfil/$usuarioId")
                        }
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón Ver Carrito
                Button(
                    onClick = { navController.navigate("carrito/$usuarioId") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD2691E)
                    )
                ) {
                    Icon(Icons.Default.ShoppingCart, "Ver carrito")
                    Text("Ver Carrito", modifier = Modifier.padding(start = 8.dp))
                }
            }

            BarraBusqueda(
                valor = busqueda,
                onValorCambio = onBusquedaCambio
            )

            FiltroCategorias(
                categorias = categorias,
                categoriaSeleccionada = categoriaSeleccionada,
                onCategoriaSeleccionada = onCategoriaSeleccionada
            )

            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFF8B4513)
                    )
                }
            } else if (productos.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "😔",
                            fontSize = 48.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = if (busqueda.isNotEmpty())
                                "No se encontraron productos con \"$busqueda\""
                            else "No hay productos disponibles",
                            fontSize = 18.sp,
                            color = Color(0xFF5D4037),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 32.dp)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(productos) { producto ->
                        ProductoItem(
                            producto = producto,
                            onAgregarAlCarrito = { producto ->
                                cartViewModel.agregarAlCarrito(usuarioId, producto)
                            }
                        )
                    }
                }
            }
        }
    }
}