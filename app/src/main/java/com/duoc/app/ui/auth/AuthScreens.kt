
package com.duoc.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
            border = androidx.compose.foundation.BorderStroke(
                1.dp,
                AzulPrincipal
            )
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
            .background(FondoCeleste)
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza),
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
                } else {
                    onLoginSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal
            )
        ) {
            Text("Iniciar sesión", color = Blanco)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = volver,
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
    onRegisterSuccess: () -> Unit,
    volver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }
    var errorMensaje by remember { mutableStateOf("") }

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
                } else {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulPrincipal
            )
        ) {
            Text(
                text = "Registrarse",
                color = Blanco,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = volver,
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
