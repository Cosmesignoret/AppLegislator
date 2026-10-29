
package com.duoc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.duoc.app.ui.auth.PantallaInicio
import com.duoc.app.ui.auth.RegistroScreen
import com.duoc.app.ui.material.MaterialScreen
import com.duoc.app.viewmodel.NoteViewModel
import com.duoc.app.screens.LoginValidadoScreen
import com.duoc.app.ui.mainmenu.MainMenuScreen
import com.duoc.app.ui.personalnotes.PersonalNotesScreen

class MainActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                var pantalla by remember { mutableStateOf("inicio") }

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    when (pantalla) {

                        "inicio" -> PantallaInicio(
                            modifier = Modifier.padding(innerPadding),
                            irLogin = { pantalla = "login" },
                            irRegistro = { pantalla = "registro" }
                        )

                        "login" -> LoginValidadoScreen(
                            modifier = Modifier.padding(innerPadding),
                            volver = { pantalla = "inicio" },
                            ingresar = { pantalla = "menu" }
                        )

                        "registro" -> RegistroScreen(
                            modifier = Modifier.padding(innerPadding),
                            onRegisterSuccess = { pantalla = "login" },
                            volver = { pantalla = "inicio" }
                        )

                        "menu" -> MainMenuScreen(
                            onNavigateToNotes = { pantalla = "notas" },
                            onNavigateToMaterial = { pantalla = "material" },
                            onLogout = { pantalla = "inicio" }
                        )

                        "notas" -> PersonalNotesScreen(
                            viewModel = noteViewModel,
                            onBack = { pantalla = "menu" }
                        )

                        "material" -> MaterialScreen(
                            volver = { pantalla = "menu" }
                        )
                    }
                }
            }
        }
    }
}
