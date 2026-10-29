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
import com.duoc.app.screens.PantallaInicio
import com.duoc.app.screens.RegistroScreen
import com.duoc.app.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                AppNavigation(noteViewModel)
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

            composable("registro") {
                RegistroScreen(
                    onRegisterSuccess = {
                        navController.navigate("main_menu") {
                            popUpTo("inicio") { inclusive = true }
                        }
                    },
                    volver = { navController.popBackStack() }
                )
            }

            composable("main_menu") {
                MainMenuScreen(
                    onNavigateToNotes = { navController.navigate("personal_notes") },
                    onNavigateToMaterial = { navController.navigate("material") },
                    onLogout = {
                        navController.navigate("inicio") {
                            popUpTo("main_menu") { inclusive = true }
                        }
                    }
                )
            }

            composable("personal_notes") {
                PersonalNotesScreen(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("material") {
                MaterialScreen(
                    volver = { navController.popBackStack() }
                )
            }
        }
    }
}
