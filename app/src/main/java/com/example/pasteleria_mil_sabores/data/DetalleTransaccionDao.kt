package com.example.pasteleria_mil_sabores.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pasteleria_mil_sabores.model.DetalleTransaccion

@Dao
interface DetalleTransaccionDao {
    @Insert
    suspend fun insertar(detalleTransaccion: DetalleTransaccion)

    @Insert
    suspend fun insertarTodos(detalles: List<DetalleTransaccion>)

    @Update
    suspend fun actualizar(detalleTransaccion: DetalleTransaccion)

    @Delete
    suspend fun eliminar(detalleTransaccion: DetalleTransaccion)

    @Query("SELECT * FROM detalle_transacciones")
    suspend fun obtenerTodos(): List<DetalleTransaccion>

    @Query("SELECT * FROM detalle_transacciones WHERE transaccionId = :transaccionId")
    suspend fun obtenerPorTransaccion(transaccionId: Int): List<DetalleTransaccion>

    @Query("SELECT * FROM detalle_transacciones WHERE transaccionId = :transaccionId AND productoId = :productoId")
    suspend fun obtenerDetalle(transaccionId: Int, productoId: Int): DetalleTransaccion?

    @Query("DELETE FROM detalle_transacciones WHERE transaccionId = :transaccionId")
    suspend fun eliminarPorTransaccion(transaccionId: Int)

    @Query("SELECT SUM(cantidad) FROM detalle_transacciones WHERE transaccionId = :transaccionId")
    suspend fun obtenerTotalProductos(transaccionId: Int): Int?
}