package com.example.pasteleria_mil_sabores.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.pasteleria_mil_sabores.model.Transaccion

@Dao
interface TransaccionDao {
    @Insert
    suspend fun insertar(transaccion: Transaccion): Long

    @Update
    suspend fun actualizar(transaccion: Transaccion)

    @Delete
    suspend fun eliminar(transaccion: Transaccion)

    @Query("SELECT * FROM transacciones")
    suspend fun obtenerTodas(): List<Transaccion>

    @Query("SELECT * FROM transacciones WHERE id = :id")
    suspend fun obtenerPorId(id: Int): Transaccion?

    @Query("SELECT * FROM transacciones WHERE usuarioId = :usuarioId ORDER BY fecha DESC")
    suspend fun obtenerPorUsuario(usuarioId: Int): List<Transaccion>

    @Query("SELECT * FROM transacciones WHERE usuarioId = :usuarioId AND estado = :estado")
    suspend fun obtenerPorUsuarioYEstado(usuarioId: Int, estado: String): List<Transaccion>

    @Query("SELECT * FROM transacciones WHERE estado = :estado ORDER BY fecha DESC")
    suspend fun obtenerPorEstado(estado: String): List<Transaccion>

    @Query("UPDATE transacciones SET estado = :nuevoEstado WHERE id = :id")
    suspend fun actualizarEstado(id: Int, nuevoEstado: String)
}