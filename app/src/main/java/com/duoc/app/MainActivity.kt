package com.duoc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duoc.app.screens.LoginScreen
import com.duoc.app.screens.MaterialScreen
import com.duoc.app.screens.LoginScreen
class MainActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

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
                            volver = { pantalla = "inicio" },
                            ingresar = { pantalla = "material" }
                        )

                        "registro" -> RegistroScreen(
                            modifier = Modifier.padding(innerPadding),
                            volver = { pantalla = "inicio" }
                        )

                        "material" -> MaterialScreen(
                            volver = { pantalla = "inicio" }
                        )
                    }

                }
            }
        }
    }
}

@Composable
fun AppNavigation(viewModel: NoteViewModel) {
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "inicio",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("inicio") {
                PantallaInicio(
                    irLogin = { navController.navigate("login") },
                    irRegistro = { navController.navigate("registro") }
                )
            }

            composable("login") {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate("main_menu") {
                            popUpTo("inicio") { inclusive = true }
                        }
                    },
                    volver = { navController.popBackStack() }
                )
            }
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
