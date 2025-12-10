package com.example.pasteleria_mil_sabores.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleria_mil_sabores.ui.components.BotonLogin
import com.example.pasteleria_mil_sabores.ui.components.CampoTexto
import com.example.pasteleria_mil_sabores.ui.components.TituloText
import com.example.pasteleria_mil_sabores.viewmodel.PerfilViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun PerfilScreen(navController: NavController, usuarioId: Int) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(true) }

    val viewModel: PerfilViewModel = viewModel()

    LaunchedEffect(usuarioId) {
        CoroutineScope(Dispatchers.IO).launch {
            val usuario = viewModel.obtenerUsuario(usuarioId)

            withContext(Dispatchers.Main) {
                if (usuario != null) {
                    nombre = usuario.nombre
                    correo = usuario.correo
                    contrasena = usuario.contrasena
                }
                cargando = false
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF5E1))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            TituloText("Mi Perfil")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Información de tu cuenta",
                color = Color(0xFFB0BEC5),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (cargando) {
                Text(
                    text = "Cargando...",
                    color = Color(0xFF5D4037)
                )
            } else {
                CampoTexto(
                    valor = nombre,
                    onValorCambio = { nombre = it },
                    etiqueta = "Nombre"
                )

                Spacer(modifier = Modifier.height(16.dp))

                CampoTexto(
                    valor = correo,
                    onValorCambio = { correo = it },
                    etiqueta = "Correo Electrónico"
                )

                Spacer(modifier = Modifier.height(16.dp))

                CampoTexto(
                    valor = contrasena,
                    onValorCambio = { contrasena = it },
                    etiqueta = "Contraseña"
                )

                Spacer(modifier = Modifier.height(24.dp))

                BotonLogin(
                    texto = "Guardar Cambios",
                    onClickAccion = {
                        CoroutineScope(Dispatchers.IO).launch {
                            viewModel.actualizarUsuario(
                                usuarioId = usuarioId,
                                nombre = nombre,
                                correo = correo,
                                contrasena = contrasena
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                BotonLogin(
                    texto = "Volver",
                    onClickAccion = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}