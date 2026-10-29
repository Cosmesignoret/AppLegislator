
package com.duoc.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import com.duoc.app.utils.SeguridadContrasena
import com.duoc.app.data.repository.SesionManager
import com.duoc.app.model.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginValidadoScreen(
    modifier: Modifier = Modifier,
    volver: () -> Unit,
    ingresar: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { AppDatabase.getDatabase(context) }
    val sesionManager = remember { SesionManager(context) }

    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    val azul = Color(0xFF205C95)
    val fondo = Color(0xFFEDF5FA)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondo)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "InfoDerecho",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF174666)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Iniciar sesión",
            fontSize = 22.sp,
            color = azul
        )

        Spacer(modifier = Modifier.height(30.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                error = ""
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                error = ""
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (error.isNotEmpty()) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = error,
                color = Color(0xFFD32F2F)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (correo.isBlank() || contrasena.isBlank()) {
                    error = "Completa todos los campos"
                } else {
                    cargando = true

                    scope.launch {
                        try {
                            val usuario = withContext(Dispatchers.IO) {
                                database.usuarioDao().buscarPorCorreo(
                                    correo.trim().lowercase()
                                )
                            }

                            val esValido = if (usuario != null) {
                                withContext(Dispatchers.Default) {
                                    SeguridadContrasena.verificar(
                                        contrasena,
                                        usuario.salt,
                                        usuario.contrasenaHash
                                    )
                                }
                            } else {
                                false
                            }

                            if (usuario != null && esValido) {
                                sesionManager.guardarSesion(usuario.id)
                                ingresar()
                            } else {
                                error = "Correo o contraseña incorrectos"
                            }
                        } catch (e: Exception) {
                            error = "No se pudo iniciar sesión"
                        } finally {
                            cargando = false
                        }
                    }
                }
            },
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = azul
            )
        ) {
            Text(
                text = if (cargando) "Ingresando..." else "Iniciar sesión"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = volver) {
            Text(
                text = "Volver",
                color = azul
            )
        }
    }
}
