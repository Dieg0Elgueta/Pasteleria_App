package com.example.pasteleria_mil_sabores.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pasteleria_mil_sabores.data.PasteleriaDatabase
import com.example.pasteleria_mil_sabores.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductoViewModel(application: Application) : AndroidViewModel(application) {
    private val database = PasteleriaDatabase.getDatabase(application)
    private val productoDao = database.productoDao()

    private val _productos = MutableStateFlow(emptyList<Producto>())
    val productos: StateFlow<List<Producto>> = _productos

    private val _categorias = MutableStateFlow(emptyList<String>())
    val categorias: StateFlow<List<String>> = _categorias

    fun cargarProductos() {
        viewModelScope.launch {
            val lista = productoDao.obtenerTodos()
            _productos.value = lista
        }
    }

    suspend fun obtenerTodosLosProductos(): List<Producto> {
        return productoDao.obtenerTodos()
    }

    fun cargarProductosDisponibles() {
        viewModelScope.launch {
            val lista = productoDao.obtenerDisponibles()
            _productos.value = lista
        }
    }

    fun cargarProductosPorCategoria(categoria: String) {
        viewModelScope.launch {
            val lista = productoDao.obtenerPorCategoria(categoria)
            _productos.value = lista
        }
    }

    fun buscarProductos(busqueda: String) {
        viewModelScope.launch {
            val lista = productoDao.buscarPorNombre(busqueda)
            _productos.value = lista
        }
    }

    suspend fun obtenerProductoPorId(id: Int): Producto? {
        return productoDao.obtenerPorId(id)
    }

    fun cargarCategorias() {
        viewModelScope.launch {
            val lista = productoDao.obtenerCategorias()
            _categorias.value = lista
        }
    }

    suspend fun obtenerCategorias(): List<String> {
        return productoDao.obtenerCategorias()
    }

    suspend fun insertarProducto(producto: Producto) {
        productoDao.insertar(producto)
        cargarProductos()
    }

    suspend fun actualizarProducto(producto: Producto) {
        productoDao.actualizar(producto)
        cargarProductos()
    }

    suspend fun eliminarProducto(producto: Producto) {
        productoDao.eliminar(producto)
        cargarProductos()
    }
}