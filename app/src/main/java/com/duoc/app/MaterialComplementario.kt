
package com.duoc.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialComplementarioScreen(volver: () -> Unit) {
    var pestana by remember { mutableStateOf(0) }

    val azul = Color(0xFF205C95)
    val azulOscuro = Color(0xFF174666)
    val fondo = Color(0xFFEDF5FA)
    val texto = Color(0xFF17232F)
    val textoSecundario = Color(0xFF607080)

    var tituloSeleccionado by remember { mutableStateOf("") }
    var descripcionSeleccionada by remember { mutableStateOf("") }
    var mostrarDetalle by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondo)
    ) {
        if (mostrarDetalle) {
            AlertDialog(
                onDismissRequest = {
                    mostrarDetalle = false
                },
                title = {
                    Text(
                        text = tituloSeleccionado,
                        color = azulOscuro
                    )
                },
                text = {
                    Text(
                        text = descripcionSeleccionada,
                        color = texto
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            mostrarDetalle = false
                        }
                    ) {
                        Text(
                            text = "Cerrar",
                            color = azul
                        )
                    }
                },
                containerColor = Color.White
            )
        }

        TopAppBar(
            title = {
                Text(
                    text = "Material Complementario",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            navigationIcon = {
                IconButton(onClick = volver) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = azul
            )
        )

        TabRow(
            selectedTabIndex = pestana,
            containerColor = Color.White,
            contentColor = azul
        ) {
            Tab(
                selected = pestana == 0,
                onClick = { pestana = 0 },
                text = {
                    Text(
                        text = "Jurisprudencia",
                        color = if (pestana == 0) azulOscuro else textoSecundario
                    )
                }
            )

            Tab(
                selected = pestana == 1,
                onClick = { pestana = 1 },
                text = {
                    Text(
                        text = "Doctrina",
                        color = if (pestana == 1) azulOscuro else textoSecundario
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (pestana == 0) {
                TarjetaComplementaria(
                    titulo = "Corte Suprema - Rol 123-2022",
                    descripcion = "Ejemplo de jurisprudencia sobre interpretación de normas y consentimiento.",
                    onClick = {
                        tituloSeleccionado = "Corte Suprema - Rol 123-2022"
                        descripcionSeleccionada =
                            "Ejemplo de jurisprudencia sobre interpretación de normas y consentimiento."
                        mostrarDetalle = true
                    }
                )

                TarjetaComplementaria(
                    titulo = "Derechos fundamentales",
                    descripcion = "Ejemplo de análisis judicial sobre la protección de derechos.",
                    onClick = {
                        tituloSeleccionado = "Derechos fundamentales"
                        descripcionSeleccionada =
                            "Ejemplo de análisis judicial sobre la protección de derechos."
                        mostrarDetalle = true
                    }
                )
            } else {
                TarjetaComplementaria(
                    titulo = "Introducción al Derecho",
                    descripcion = "Conceptos básicos sobre normas jurídicas y sus características.",
                    onClick = {
                        tituloSeleccionado = "Introducción al Derecho"
                        descripcionSeleccionada =
                            "Conceptos básicos sobre normas jurídicas y sus características."
                        mostrarDetalle = true
                    }
                )

                TarjetaComplementaria(
                    titulo = "Interpretación jurídica",
                    descripcion = "Material de estudio sobre cómo se interpretan las leyes.",
                    onClick = {
                        tituloSeleccionado = "Interpretación jurídica"
                        descripcionSeleccionada =
                            "Material de estudio sobre cómo se interpretan las leyes."
                        mostrarDetalle = true
                    }
                )
            }
        }
    }
}

@Composable
fun TarjetaComplementaria(
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFF17232F)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF607080)
            )
        }
    }
}
