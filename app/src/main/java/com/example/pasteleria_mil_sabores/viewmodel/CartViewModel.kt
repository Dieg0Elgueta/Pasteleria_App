package com.example.pasteleria_mil_sabores.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleria_mil_sabores.data.PasteleriaDatabase
import com.example.pasteleria_mil_sabores.model.CartItem
import com.example.pasteleria_mil_sabores.model.Producto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(application: Application) : AndroidViewModel(application) {
    private val database = PasteleriaDatabase.getDatabase(application)
    private val cartDao = database.cartDao()
    private val productoDao = database.productoDao()

    private val _carritoItems = MutableStateFlow<List<CartItem>>(emptyList())
    val carritoItems: StateFlow<List<CartItem>> = _carritoItems

    // Agregar producto al carrito
    fun agregarAlCarrito(usuarioId: Int, producto: Producto, cantidad: Int = 1) {
        viewModelScope.launch {
            val itemExistente = cartDao.obtenerItem(usuarioId, producto.id)

            if (itemExistente != null) {
                // Actualizar cantidad si ya existe
                val nuevoItem = itemExistente.copy(
                    cantidad = itemExistente.cantidad + cantidad
                )
                cartDao.actualizar(nuevoItem)
            } else {
                // Crear nuevo item
                val nuevoItem = CartItem(
                    usuarioId = usuarioId,
                    productoId = producto.id,
                    cantidad = cantidad,
                    precioUnitario = producto.precio
                )
                cartDao.insertar(nuevoItem)
            }
            cargarCarrito(usuarioId)
        }
    }

    fun actualizarCantidad(usuarioId: Int, item: CartItem, nuevaCantidad: Int) {
        viewModelScope.launch {
            if (nuevaCantidad > 0) {
                val itemActualizado = item.copy(cantidad = nuevaCantidad)
                cartDao.actualizar(itemActualizado)
            } else {
                cartDao.eliminar(item)
            }
            cargarCarrito(usuarioId)
        }
    }

    fun eliminarDelCarrito(usuarioId: Int, item: CartItem) {
        viewModelScope.launch {
            cartDao.eliminar(item)
            cargarCarrito(usuarioId)
        }
    }

    fun cargarCarrito(usuarioId: Int) {
        viewModelScope.launch {
            cartDao.obtenerPorUsuario(usuarioId).collect { items ->
                _carritoItems.value = items
            }
        }
    }

    fun obtenerCantidadTotal(usuarioId: Int): Flow<Int> {
        return cartDao.obtenerCantidadTotal(usuarioId)
    }

    fun obtenerTotalCarrito(usuarioId: Int): Flow<Int> {
        return cartDao.obtenerTotalCarrito(usuarioId)
    }

    fun limpiarCarrito(usuarioId: Int) {
        viewModelScope.launch {
            cartDao.limpiarCarrito(usuarioId)
            cargarCarrito(usuarioId)
        }
    }
}