
package com.duoc.app.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

import com.duoc.app.data.repository.MockDocumentRepository
import com.duoc.app.data.repository.MockLegislativeChangeRepository
import com.duoc.app.data.repository.UserPreferences
import com.duoc.app.data.repository.SesionManager

import com.duoc.app.ui.auth.LoginValidadoScreen
import com.duoc.app.ui.auth.PantallaInicio
import com.duoc.app.ui.auth.RegistroScreen
import com.duoc.app.ui.history.LegislativeChangesScreen
import com.duoc.app.ui.list.DocumentListScreen
import com.duoc.app.ui.mainmenu.MainMenuScreen
import com.duoc.app.ui.material.MaterialScreen
import com.duoc.app.ui.notes.PersonalNotesScreen
import com.duoc.app.ui.profile.ProfileScreen
import com.duoc.app.ui.reader.ReaderScreen
import com.duoc.app.ui.settings.ConfiguracionScreen

import com.duoc.app.viewmodel.DocumentListViewModel
import com.duoc.app.viewmodel.LegislativeChangesViewModel
import com.duoc.app.viewmodel.NoteViewModel
import com.duoc.app.viewmodel.ReaderViewModel

@Composable
fun AppNavigation(
    noteViewModel: NoteViewModel,
    modoOscuro: Boolean,
    onModoOscuroChange: (Boolean) -> Unit
) {

    val navController = rememberNavController()
    val context = LocalContext.current

    val repository = remember {
        MockDocumentRepository()
    }

    val preferences = remember(context) {
        UserPreferences(context)
    }

    val changesRepository = remember {
        MockLegislativeChangeRepository()
    }

    val sesionManager = remember(context) {
        SesionManager(context)
    }

    val startRoute = remember(sesionManager) {
        if (sesionManager.sesionIniciada()) {
            Routes.MENU
        } else {
            Routes.HOME
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = startRoute,
            modifier = Modifier.padding(innerPadding)
        ) {

            composable(Routes.HOME) {
                PantallaInicio(
                    modifier = Modifier,
                    irLogin = {
                        navController.navigate(Routes.LOGIN)
                    },
                    irRegistro = {
                        navController.navigate(Routes.REGISTER)
                    }
                )
            }

            composable(Routes.REGISTER) {
                RegistroScreen(
                    modifier = Modifier,
                    onRegisterSuccess = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.HOME)
                            launchSingleTop = true
                        }
                    },
                    volver = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.LOGIN) {
                LoginValidadoScreen(
                    modifier = Modifier,
                    volver = {
                        navController.popBackStack()
                    },
                    ingresar = { _, _ ->
                        sesionManager.guardarSesion(1)

                        navController.navigate(Routes.MENU) {
                            popUpTo(Routes.HOME) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Routes.MENU) {
                MainMenuScreen(
                    onNavigateToNotes = {
                        navController.navigate(Routes.NOTES)
                    },
                    onNavigateToMaterial = {
                        navController.navigate(Routes.MATERIAL)
                    },
                    onNavigateToLegalDocuments = {
                        navController.navigate(Routes.ARTICLES)
                    },
                    onNavigateToHistoryChanges = {
                        navController.navigate(Routes.LEGISLATIVE_CHANGES)
                    },
                    onNavigateToProfile = {
                        navController.navigate(Routes.PROFILE)
                    },

                    onNavigateToSettings = {
                        navController.navigate(Routes.SETTINGS)
                    },
                    onLogout = {
                        sesionManager.cerrarSesion()

                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.MENU) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                )
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.SETTINGS) {
                ConfiguracionScreen(
                    modoOscuro = modoOscuro,
                    onModoOscuroChange = onModoOscuroChange,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.NOTES) {
                PersonalNotesScreen(
                    viewModel = noteViewModel,
                    onBack = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Routes.MATERIAL) {
                MaterialScreen {
                    navController.popBackStack()
                }
            }

            composable(Routes.ARTICLES) {
                val vm: DocumentListViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            DocumentListViewModel(repository)
                        }
                    }
                )

                DocumentListScreen(
                    onDocumentClick = { id ->
                        navController.navigate(Routes.reader(id))
                    },
                    onBack = {
                        navController.popBackStack()
                    },
                    viewModel = vm
                )
            }

            composable(
                route = Routes.READER,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments
                    ?.getString("id")
                    ?: return@composable

                val vm: ReaderViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            ReaderViewModel(
                                repository,
                                preferences
                            )
                        }
                    }
                )

                ReaderScreen(
                    documentId = id,
                    onBack = {
                        navController.popBackStack()
                    },
                    viewModel = vm
                )
            }

            composable(Routes.LEGISLATIVE_CHANGES) {
                val vm: LegislativeChangesViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            LegislativeChangesViewModel(
                                changesRepository
                            )
                        }
                    }
                )

                LegislativeChangesScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    viewModel = vm
                )
            }
        }
    }
}

private object Routes {
    const val HOME = "home"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val MENU = "menu"
    const val PROFILE = "profile"
    const val SETTINGS = "settings"
    const val NOTES = "notes"
    const val MATERIAL = "material"
    const val ARTICLES = "articles"
    const val READER = "reader/{id}"
    const val LEGISLATIVE_CHANGES = "legislative_changes"

    fun reader(id: String): String = "reader/$id"
}
