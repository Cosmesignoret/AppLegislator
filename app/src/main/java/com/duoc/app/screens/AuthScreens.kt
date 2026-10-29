
package com.duoc.app.screens

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.app.R
import com.duoc.app.utils.SeguridadContrasena
import com.duoc.app.model.AppDatabase
import com.duoc.app.model.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private val fondo = Color(0xFFEDF5FA)
private val azul = Color(0xFF205C95)
private val azulOscuro = Color(0xFF174666)
private val texto = Color(0xFF17232F)
private val gris = Color(0xFF607080)

@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    irLogin: () -> Unit,
    irRegistro: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondo)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza2),
            contentDescription = "Logo InfoDerecho",
            modifier = Modifier.size(125.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "InfoDerecho",
            style = MaterialTheme.typography.headlineLarge,
            color = azulOscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido a nuestra aplicación",
            color = gris
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = irLogin,
                colors = ButtonDefaults.buttonColors(
                    containerColor = azul,
                    contentColor = Color.White
                )
            ) {
                Text("Iniciar sesión")
            }

            OutlinedButton(
                onClick = irRegistro,
                border = BorderStroke(1.dp, azul),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = azul
                )
            ) {
                Text("Regístrate")
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    volver: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(fondo)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza2),
            contentDescription = "Logo InfoDerecho",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "InfoDerecho",
            style = MaterialTheme.typography.headlineMedium,
            color = azulOscuro
        )

        TextField(
            value = correo,
            onValueChange = {
                correo = it
                errorMensaje = ""
            },
            label = { Text("Correo") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = texto,
                unfocusedTextColor = texto,
                focusedLabelColor = azul,
                unfocusedLabelColor = gris,
                focusedIndicatorColor = azul
            )
        )

        TextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorMensaje = ""
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = texto,
                unfocusedTextColor = texto,
                focusedLabelColor = azul,
                unfocusedLabelColor = gris,
                focusedIndicatorColor = azul
            )
        )

        if (errorMensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = errorMensaje,
                color = Color(0xFFD32F2F)
            )
        }

        Button(
            onClick = {
                errorMensaje = "Utiliza el inicio de sesión validado"
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = azul,
                contentColor = Color.White
            )
        ) {
            Text("Iniciar sesión")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            border = BorderStroke(1.dp, azul),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = azul
            )
        ) {
            Text("Volver al inicio")
        }
    }
}

@Composable
fun RegistroScreen(
    modifier: Modifier = Modifier,
    onRegisterSuccess: () -> Unit,
    volver: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val database = remember { AppDatabase.getDatabase(context) }

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var confirmar by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

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
            text = "Crear cuenta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = azulOscuro
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                error = ""
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = confirmar,
            onValueChange = {
                confirmar = it
                error = ""
            },
            label = { Text("Confirmar contraseña") },
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
                when {
                    nombre.isBlank() ||
                            correo.isBlank() ||
                            contrasena.isBlank() ||
                            confirmar.isBlank() -> {
                        error = "Completa todos los campos"
                    }

                    !Patterns.EMAIL_ADDRESS.matcher(
                        correo.trim()
                    ).matches() -> {
                        error = "Ingresa un correo válido"
                    }

                    contrasena.length < 8 -> {
                        error = "La contraseña debe tener al menos 8 caracteres"
                    }

                    contrasena != confirmar -> {
                        error = "Las contraseñas no coinciden"
                    }

                    else -> {
                        cargando = true
                        error = ""

                        scope.launch {
                            try {
                                val correoNormalizado =
                                    correo.trim().lowercase()

                                val existe = withContext(Dispatchers.IO) {
                                    database.usuarioDao()
                                        .buscarPorCorreo(correoNormalizado)
                                }

                                if (existe != null) {
                                    error = "Este correo ya está registrado"
                                } else {
                                    val salt = withContext(
                                        Dispatchers.Default
                                    ) {
                                        SeguridadContrasena.generarSalt()
                                    }

                                    val hash = withContext(
                                        Dispatchers.Default
                                    ) {
                                        SeguridadContrasena.generarHash(
                                            contrasena,
                                            salt
                                        )
                                    }

                                    val usuario = UsuarioEntity(
                                        nombre = nombre.trim(),
                                        correo = correoNormalizado,
                                        contrasenaHash = hash,
                                        salt = salt
                                    )

                                    withContext(Dispatchers.IO) {
                                        database.usuarioDao()
                                            .registrarUsuario(usuario)
                                    }

                                    onRegisterSuccess()
                                }
                            } catch (e: Exception) {
                                android.util.Log.e("REGISTRO", "Error al registrar usuario", e)
                                error = "No se pudo registrar la cuenta"
                            } finally {
                                cargando = true
                            }
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
                text = if (cargando) {
                    "Registrando..."
                } else {
                    "Registrarse"
                }
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
