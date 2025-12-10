package com.example.pasteleria_mil_sabores.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transacciones",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Transaccion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioId: Int,
    val fecha: String,
    val total: Int,
    val estado: String = "Pendiente",
    val direccionEntrega: String = "",
    val fechaEntrega: String = ""
)