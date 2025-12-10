package com.example.pasteleria_mil_sabores.data

import androidx.room.*
import com.example.pasteleria_mil_sabores.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(item: CartItem)

    @Update
    suspend fun actualizar(item: CartItem)

    @Delete
    suspend fun eliminar(item: CartItem)

    @Query("DELETE FROM carrito WHERE usuarioId = :usuarioId")
    suspend fun limpiarCarrito(usuarioId: Int)

    @Query("SELECT * FROM carrito WHERE usuarioId = :usuarioId")
    fun obtenerPorUsuario(usuarioId: Int): Flow<List<CartItem>>

    @Query("SELECT * FROM carrito WHERE usuarioId = :usuarioId AND productoId = :productoId")
    suspend fun obtenerItem(usuarioId: Int, productoId: Int): CartItem?

    @Query("SELECT COALESCE(SUM(cantidad * precioUnitario), 0) FROM carrito WHERE usuarioId = :usuarioId")
    fun obtenerCantidadTotal(usuarioId: Int): Flow<Int>

    @Query("SELECT COALESCE(SUM(cantidad * precioUnitario), 0) FROM carrito WHERE usuarioId = :usuarioId")
    fun obtenerTotalCarrito(usuarioId: Int): Flow<Int>
}