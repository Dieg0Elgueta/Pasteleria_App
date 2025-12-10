package com.example.pasteleria_mil_sabores.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "detalle_transacciones",
    primaryKeys = ["transaccionId", "productoId"],
    foreignKeys = [
        ForeignKey(
            entity = Transaccion::class,
            parentColumns = ["id"],
            childColumns = ["transaccionId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Producto::class,
            parentColumns = ["id"],
            childColumns = ["productoId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DetalleTransaccion(
    val transaccionId: Int,
    val productoId: Int,
    val cantidad: Int
)