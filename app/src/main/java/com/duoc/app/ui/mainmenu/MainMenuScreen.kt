
package com.duoc.app.ui.mainmenu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.SyncLock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val AzulPrincipal = Color(0xFF205C95)
private val AzulOscuro = Color(0xFF174666)
private val FondoCeleste = Color(0xFFEDF5FA)
private val AzulTarjetas = Color(0xFFDCEAF5)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onNavigateToNotes: () -> Unit,
    onNavigateToMaterial: () -> Unit,
    onLogout: () -> Unit,
    onNavigateToLegalDocuments: () -> Unit,
    onNavigateToHistoryChanges: () -> Unit
) {
    var busqueda by remember { mutableStateOf("") }

    val modulos = listOf(
        "Código Civil",
        "Código Penal",
        "Contenidos",
        "Aclaraciones",
        "Actividades"
    )

    Scaffold(
        containerColor = FondoCeleste,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "AppLegislaor - Principal",
                        color = Color.White,
                        fontSize = 20.sp
                    )
                },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(
                            Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulPrincipal
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = AzulTarjetas
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Bienvenido a AppLegislator",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        Text(
                            "Tu espacio de estudio jurídico.",
                            fontSize = 14.sp,
                            color = AzulOscuro
                        )
                    }
                }
            }

            item {
                OutlinedTextField(
                    value = busqueda,
                    onValueChange = { busqueda = it },
                    label = { Text("Buscar leyes o artículos") },
                    placeholder = { Text("Escribe aquí...") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = AzulPrincipal,
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White
                    )
                )
            }

            item {
                Text(
                    "Explorar contenidos",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
            }

            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    modulos.chunked(3).forEach { fila ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            fila.forEach { modulo ->
                                Card(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(110.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Column(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(8.dp),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = when (modulo) {
                                                "Código Civil" -> "⚖"
                                                "Código Penal" -> "▤"
                                                "Contenidos" -> "▣"
                                                "Aclaraciones" -> "?"
                                                else -> "✓"
                                            },
                                            fontSize = 30.sp,
                                            color = AzulPrincipal
                                        )

                                        Spacer(Modifier.height(8.dp))

                                        Text(
                                            text = modulo,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.Center,
                                            color = AzulOscuro
                                        )
                                    }
                                }
                            }

                            repeat(3 - fila.size) {
                                Spacer(Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    "Alertas de Reformas",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Actualizaciones legales",
                            fontWeight = FontWeight.Bold,
                            color = AzulOscuro
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Aquí aparecerán las novedades y reformas de la legislación chilena.",
                            fontSize = 13.sp
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Sección informativa de ejemplo",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                Text(
                    "Opciones del Sistema",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
            }

            item {
                MenuOptionCard(
                    title = "Mis Notas de Estudio",
                    subtitle = "Crea y guarda apuntes locales con foto y Room",
                    icon = Icons.Default.Edit,
                    onClick = onNavigateToNotes
                )
            }

            item {
                MenuOptionCard(
                    title = "Material Complementario",
                    subtitle = "Consulta jurisprudencia y doctrina",
                    icon = Icons.Default.Book,
                    onClick = onNavigateToMaterial
                )
            }

            item {
                MenuOptionCard(
                    title = "Artículos Legales",
                    subtitle = "Buscar en el archivo legal",
                    icon = Icons.Default.Description,
                    onClick = onNavigateToLegalDocuments
                )
            }

            item {
                MenuOptionCard (
                    title = "Cambios Legislativos",
                    subtitle = "Historial de cambios legislativos",
                    icon = Icons.Default.SyncLock,
                    onClick = onNavigateToHistoryChanges
                )
            }
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
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
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
                tint = AzulPrincipal,
                modifier = Modifier.size(36.dp)
            )

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AzulOscuro
                )
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
