package com.duoc.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.duoc.app.preferences.UserPreferences
import com.duoc.app.ui.list.DocumentListScreen
import com.duoc.app.ui.list.DocumentListViewModel
import com.duoc.app.ui.reader.ReaderScreen
import com.duoc.app.ui.reader.ReaderViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val repository = remember { MockDocumentRepository() }
    val preferences = remember { UserPreferences(context) }

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            val vm: DocumentListViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { DocumentListViewModel(repository) }
                }
            )
            DocumentListScreen(
                onDocumentClick = { id -> navController.navigate("reader/$id") },
                viewModel = vm
            )
        }

        composable(
            route = "reader/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: return@composable
            val vm: ReaderViewModel = viewModel(
                factory = viewModelFactory {
                    initializer { ReaderViewModel(repository, preferences) }
                }
            )
            ReaderScreen(
                documentId = id,
                onBack = { navController.popBackStack() },
                viewModel = vm
            )
        }
    }
}
