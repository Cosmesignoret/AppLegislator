package com.duoc.app.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.duoc.app.R

@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    irLogin: () -> Unit,
    irRegistro: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF282B38))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.balanza),
            contentDescription = "Logo AppLegislator",
            modifier = Modifier.size(125.dp),
            contentScale = ContentScale.Fit
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AppLegislator",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido a nuestra aplicación",
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = irLogin) {
                Text("Iniciar sesión")
            }

            OutlinedButton(onClick = irRegistro) {
                Text("Regístrate", color = Color.White)
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
            .background(Color(0xFF282B38))
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
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        TextField(
            value = correo,
            onValueChange = {
                correo = it
                errorMensaje = ""
            },
            label = { Text("Correo") },
            modifier = Modifier.padding(top = 20.dp)
        )

        TextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                errorMensaje = ""
            },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(top = 12.dp)
        )

        if (errorMensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMensaje, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (correo.isBlank() || contrasena.isBlank()) {
                    errorMensaje = "Por favor, completa los campos"
                } else {
                    onLoginSuccess()
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Iniciar sesión")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Volver al inicio", color = Color.White)
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
            .background(Color(0xFF282B38))
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.padding(top = 20.dp)
        )

        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.padding(top = 12.dp)
        )

        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(top = 12.dp)
        )

        if (errorMensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = errorMensaje, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (nombre.isBlank() || correo.isBlank() || contrasena.isBlank()) {
                    errorMensaje = "Todos los campos son obligatorios"
                } else {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Registrarse")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Volver al inicio", color = Color.White)
        }
    }
}
