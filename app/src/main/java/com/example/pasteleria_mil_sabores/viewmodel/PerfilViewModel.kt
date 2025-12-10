package com.example.pasteleria_mil_sabores.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pasteleria_mil_sabores.data.PasteleriaDatabase
import com.example.pasteleria_mil_sabores.model.Usuario

class PerfilViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao = PasteleriaDatabase.getDatabase(application).usuarioDao()

    suspend fun obtenerUsuario(usuarioId: Int): Usuario? {
        return usuarioDao.obtenerPorId(usuarioId)
    }

    suspend fun actualizarUsuario(usuarioId: Int, nombre: String, correo: String, contrasena: String) {
        val usuario = Usuario(
            id = usuarioId,
            nombre = nombre,
            correo = correo,
            contrasena = contrasena
        )
        usuarioDao.actualizar(usuario)
    }
}