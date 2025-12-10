package com.example.pasteleria_mil_sabores.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
data class Producto(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val categoria: String,
    val precio: Int,
    val descripcion: String,
    val imagen: String?,
    val disponible: Boolean = true
)