<<<<<<< HEAD:app/src/main/java/com/duoc/app/ui/mainmenu/MainMenuScreen.kt
package com.duoc.app.ui.mainmenu
=======

package com.duoc.app
>>>>>>> a484bad (se creo la validacion de correo y contraseña, tambien para guardarlo y mantenerlo):app/src/main/java/com/duoc/app/MainMenuScreen.kt

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigateToNotes: () -> Unit,
    onNavigateToMaterial: () -> Unit,
    onLogout: () -> Unit
) {
    val azul = Color(0xFF205C95)
    val azulOscuro = Color(0xFF174666)
    val fondo = Color(0xFFEDF5FA)
    val texto = Color(0xFF17232F)
    val textoSecundario = Color(0xFF607080)

    Scaffold(
        containerColor = fondo,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AppLegislator - Menú Principal",
                        color = Color.White
                    )
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = azul
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Bienvenido a AppLegislator",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = azulOscuro
                    )

                    Text(
                        text = "Gestión de apuntes, notas y estudio jurídico.",
                        fontSize = 14.sp,
                        color = textoSecundario
                    )
                }
            }

            Text(
                text = "Opciones del Sistema",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = texto
            )

            MenuOptionCard(
                title = "Mis Notas de Estudio",
                subtitle = "Crea y guarda apuntes locales con foto y Room",
                icon = Icons.Default.Edit,
                onClick = onNavigateToNotes
            )

            MenuOptionCard(
                title = "Material Complementario",
                subtitle = "Consulta jurisprudencia y doctrina",
                icon = Icons.Default.Book,
                onClick = onNavigateToMaterial
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF205C95),
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17232F)
                )

                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color(0xFF607080)
                )
            }
        }
    }
}
