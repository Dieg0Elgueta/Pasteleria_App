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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.pasteleria_mil_sabores.model.Usuario
import com.example.pasteleria_mil_sabores.ui.components.BotonLogin
import com.example.pasteleria_mil_sabores.ui.components.CampoTexto
import com.example.pasteleria_mil_sabores.ui.components.TituloText
import com.example.pasteleria_mil_sabores.viewmodel.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegistroScreen(navController: NavController) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var repetirContrasena by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    val viewModel: LoginViewModel = viewModel()

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
            TituloText("Crear Cuenta")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Regístrate para continuar",
                color = Color(0xFFB0BEC5),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            CampoTexto(
                valor = nombre,
                onValorCambio = { nombre = it },
                etiqueta = "Nombre Completo"
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
                etiqueta = "Contraseña",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = repetirContrasena,
                onValorCambio = { repetirContrasena = it },
                etiqueta = "Repetir Contraseña",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = if (mensaje == "Registro exitoso") Color(0xFF4CAF50) else Color(0xFFD32F2F),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            BotonLogin(
                texto = "Crear Cuenta",
                onClickAccion = {
                    CoroutineScope(Dispatchers.IO).launch {

                        if (contrasena != repetirContrasena) {
                            withContext(Dispatchers.Main) {
                                mensaje = "Las contraseñas no coinciden"
                            }
                            return@launch
                        }

                        val nuevoUsuario = Usuario(nombre = nombre, correo = correo, contrasena = contrasena)
                        viewModel.registroUsuario(nuevoUsuario)


                        val registroExitoso = nombre.isNotBlank() && correo.isNotBlank() && contrasena.isNotBlank()

                        withContext(Dispatchers.Main) {
                            if (registroExitoso) {
                                mensaje = "Registro exitoso"
                                navController.navigate("login") {
                                    popUpTo("registro") { inclusive = true }
                                }
                            } else {
                                mensaje = "Por favor completa todos los campos"
                            }
                        }
                    }
                }
            )
        }
    }
}
