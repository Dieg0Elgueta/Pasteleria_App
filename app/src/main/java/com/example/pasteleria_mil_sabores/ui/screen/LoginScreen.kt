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
fun LoginScreen(navController: NavController) {
    var usuario by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
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
            TituloText("Pastelería Mil Sabores")

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "¡Celebrando 50 años de dulzura!",
                color = Color(0xFFB0BEC5),
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = usuario,
                onValorCambio = { usuario = it },
                etiqueta = "Correo Electrónico"
            )

            Spacer(modifier = Modifier.height(16.dp))

            CampoTexto(
                valor = contrasena,
                onValorCambio = { contrasena = it },
                etiqueta = "Contraseña",
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (mensaje.isNotEmpty()) {
                Text(
                    text = mensaje,
                    color = if (mensaje == "Login exitoso") Color(0xFF4CAF50) else Color(0xFFD32F2F),
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            BotonLogin(
                texto = "Iniciar Sesión",
                onClickAccion = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val loginExitoso = viewModel.login(usuario, contrasena)

                        withContext(Dispatchers.Main) {
                            if (loginExitoso) {
                                mensaje = "Login exitoso"
                                val usuarioLogueado = viewModel.obtenerUsuarioActual()
                                navController.navigate("home/${usuarioLogueado?.id}") {
                                    popUpTo("login") { inclusive = true }
                                }
                            } else {
                                mensaje = "Usuario o contraseña incorrectos"
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            BotonLogin(
                texto = "Registrarse",
                onClickAccion = {
                    navController.navigate("registro")
                }
            )


        }
    }
}