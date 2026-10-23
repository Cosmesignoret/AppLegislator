
package com.duoc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var pantalla by remember { mutableStateOf("inicio") }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    when (pantalla) {
                        "inicio" -> PantallaInicio(
                            modifier = Modifier.padding(innerPadding),
                            irLogin = { pantalla = "login" },
                            irRegistro = { pantalla = "registro" }
                        )

                        "login" -> LoginScreen(
                            modifier = Modifier.padding(innerPadding),
                            volver = { pantalla = "inicio" }
                        )

                        "registro" -> RegistroScreen(
                            modifier = Modifier.padding(innerPadding),
                            volver = { pantalla = "inicio" }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PantallaInicio(
    modifier: Modifier = Modifier,
    irLogin: () -> Unit,
    irRegistro: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "InfoDerecho",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Bienvenido a nuestra aplicación")

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = irLogin) {
                Text("Iniciar sesión")
            }

            OutlinedButton(onClick = irRegistro) {
                Text("Regístrate")
            }
        }
    }
}

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    volver: () -> Unit
) {
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Iniciar sesión",
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.padding(top = 20.dp)
        )

        TextField(
            value = contrasena,
            onValueChange = { contrasena = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(top = 12.dp)
        )

        Button(
            onClick = {},
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Iniciar sesión")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}

@Composable
fun RegistroScreen(
    modifier: Modifier = Modifier,
    volver: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var contrasena by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Crear cuenta",
            style = MaterialTheme.typography.headlineMedium
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

        Button(
            onClick = {},
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text("Registrarse")
        }

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PantallaInicioPreview() {
    MaterialTheme {
        PantallaInicio(
            irLogin = {},
            irRegistro = {}
        )
    }
}
