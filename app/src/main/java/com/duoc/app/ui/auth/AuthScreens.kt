package com.duoc.app.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.app.R
import kotlinx.coroutines.launch

private val AzulPrincipal = Color(0xFF205C95)
private val AzulOscuro = Color(0xFF174666)
private val FondoCeleste = Color(0xFFEDF5FA)
private val Blanco = Color.White
private val RojoError = Color(0xFFB3261E)

@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    irLogin: () -> Unit,
    irRegistro: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoCeleste)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza2),
            contentDescription = "Logo AppLegislator",
            modifier = Modifier.size(125.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AppLegislator",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "¡Bienvenido!",
            style = MaterialTheme.typography.titleLarge,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu espacio para aprender y organizar tus estudios jurídicos.",
            color = AzulOscuro,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = irLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal
            )
        ) {
            Text(
                text = "Iniciar sesión",
                color = Blanco,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = irRegistro,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Blanco
            ),
            border = BorderStroke(1.dp, AzulPrincipal)
        ) {
            Text(
                text = "Registrarse",
                color = AzulPrincipal,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun LoginValidadoScreen(
    modifier: Modifier = Modifier,
    ingresar: suspend (correo: String, contrasena: String) -> Result<Unit>,
    volver: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoCeleste)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza2),
            contentDescription = "Logo AppLegislator",
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "AppLegislator",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                errorMensaje = ""
            },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AzulOscuro,
                unfocusedTextColor = AzulOscuro,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Blanco,
                unfocusedContainerColor = Blanco
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorMensaje = ""
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AzulOscuro,
                unfocusedTextColor = AzulOscuro,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Blanco,
                unfocusedContainerColor = Blanco
            )
        )

        if (errorMensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMensaje, color = RojoError)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (correo.isBlank() || contrasena.isBlank()) {
                    errorMensaje = "Por favor, completa los campos"
                    return@Button
                }
                scope.launch {
                    cargando = true
                    errorMensaje = ""
                    ingresar(correo.trim(), contrasena)
                        .onFailure { e ->
                            errorMensaje = if (e is retrofit2.HttpException) {
                                val errorBody = e.response()?.errorBody()?.string()
                                if (!errorBody.isNullOrEmpty()) {
                                    try {
                                        org.json.JSONObject(errorBody)
                                            .optString("message", "El correo o la contraseña es incorrecto")
                                    } catch (jsonEx: Exception) {
                                        errorBody
                                    }
                                } else {
                                    "El correo o la contraseña es incorrecto"
                                }
                            } else {
                                e.message ?: "El correo o la contraseña es incorrecto"
                            }
                        }
                    cargando = false
                }
            },
            enabled = !cargando,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal
            )
        ) {
            if (cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = Blanco,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Iniciar sesión", color = Blanco)
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = volver,
            enabled = !cargando,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Blanco
            )
        ) {
            Text("Volver al inicio", color = AzulPrincipal)
        }
    }
}

@Composable
fun RegistroScreen(
    modifier: Modifier = Modifier,
    registrar: suspend (nombre: String, correo: String, contrasena: String) -> Result<Unit>,
    volver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }
    var cargando by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(FondoCeleste)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza2),
            contentDescription = "Logo AppLegislator",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "AppLegislator",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.titleLarge,
            color = AzulOscuro
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = {
                nombre = it
                errorMensaje = ""
            },
            label = { Text("Nombre completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AzulOscuro,
                unfocusedTextColor = AzulOscuro,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Blanco,
                unfocusedContainerColor = Blanco
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = {
                correo = it
                errorMensaje = ""
            },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AzulOscuro,
                unfocusedTextColor = AzulOscuro,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Blanco,
                unfocusedContainerColor = Blanco
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorMensaje = ""
            },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            enabled = !cargando,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = AzulOscuro,
                unfocusedTextColor = AzulOscuro,
                focusedBorderColor = AzulPrincipal,
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Blanco,
                unfocusedContainerColor = Blanco
            )
        )

        if (errorMensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMensaje, color = RojoError)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (
                    nombre.isBlank() ||
                    correo.isBlank() ||
                    contrasena.isBlank()
                ) {
                    errorMensaje = "Todos los campos son obligatorios"
                    return@Button
                }
                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(correo.trim()).matches()) {
                    errorMensaje = "Ingresa un correo electrónico válido"
                    return@Button
                }
                if (contrasena.length < 6) {
                    errorMensaje = "La contraseña debe tener al menos 6 caracteres"
                    return@Button
                }
                scope.launch {
                    cargando = true
                    errorMensaje = ""
                    registrar(nombre.trim(), correo.trim(), contrasena)
                        .onFailure { e ->
                            errorMensaje = e.message ?: "No se pudo registrar"
                        }
                    cargando = false
                }
            },
            enabled = !cargando,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal
            )
        ) {
            if (cargando) {
                CircularProgressIndicator(
                    modifier = Modifier.size(22.dp),
                    color = Blanco,
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Registrarse",
                    color = Blanco,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = volver,
            enabled = !cargando,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Blanco
            )
        ) {
            Text(
                text = "Volver al inicio",
                color = AzulPrincipal
            )
        }
    }
}