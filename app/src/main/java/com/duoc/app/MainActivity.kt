
package com.duoc.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.duoc.app.data.repository.ThemePreferences
import com.duoc.app.ui.navigation.AppNavigation
import com.duoc.app.ui.theme.AppLegislatorTheme
import com.duoc.app.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val themePreferences = ThemePreferences(applicationContext)

        setContent {
            var modoOscuro by androidx.compose.runtime.remember {
                mutableStateOf(themePreferences.obtenerModoOscuro())
            }

            AppLegislatorTheme(darkTheme = modoOscuro) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        noteViewModel = noteViewModel,
                        modoOscuro = modoOscuro,
                        onModoOscuroChange = { activado ->
                            modoOscuro = activado
                            themePreferences.guardarModoOscuro(activado)
                        }
                    )
                }
            }
        }
    }
}
