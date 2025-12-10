package com.example.pasteleria_mil_sabores.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pasteleria_mil_sabores.model.Producto

@Dao
interface ProductoDao {
    @Insert
    suspend fun insertar(producto: Producto)

    @Update
    suspend fun actualizar(producto: Producto)

    @Delete
    suspend fun eliminar(producto: Producto)

    @Query("SELECT * FROM productos")
    suspend fun obtenerTodos(): List<Producto>

    @Query("SELECT * FROM productos WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Producto?

    @Query("SELECT * FROM productos WHERE categoria = :categoria")
    suspend fun obtenerPorCategoria(categoria: String): List<Producto>

    @Query("SELECT * FROM productos WHERE disponible = 1")
    suspend fun obtenerDisponibles(): List<Producto>

    @Query("SELECT * FROM productos WHERE nombre LIKE '%' || :busqueda || '%'")
    suspend fun buscarPorNombre(busqueda: String): List<Producto>

    @Query("SELECT DISTINCT categoria FROM productos ORDER BY categoria")
    suspend fun obtenerCategorias(): List<String>
}