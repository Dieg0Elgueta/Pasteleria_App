package com.example.pasteleria_mil_sabores.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.pasteleria_mil_sabores.data.PasteleriaDatabase
import com.example.pasteleria_mil_sabores.model.Usuario

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val usuarioDao = PasteleriaDatabase.getDatabase(application).usuarioDao()


    private var usuarioActual: Usuario? = null

    suspend fun login(correo: String, contrasena: String): Boolean {
        val usuario = usuarioDao.login(correo, contrasena)
        return if (usuario != null) {
            usuarioActual = usuario
            true
        } else {
            false
        }
    }

    suspend fun registroUsuario(usuario: Usuario){
        usuarioDao.insertar(usuario)
    }

    fun obtenerUsuarioActual(): Usuario? {
        return usuarioActual
    }

}