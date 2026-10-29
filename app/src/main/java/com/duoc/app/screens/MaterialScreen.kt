package com.duoc.app.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MaterialScreen(volver: () -> Unit) {
    var pestana by remember { mutableStateOf(0) }
    val azul = Color(0xFF205C95)
    var tituloSeleccionado by remember { mutableStateOf("") }
    var descripcionSeleccionada by remember { mutableStateOf("") }
    var mostrarDetalle by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F5F9))
    ) {
        if (mostrarDetalle) {
            AlertDialog(
                onDismissRequest = { mostrarDetalle = false },
                title = { Text(tituloSeleccionado) },
                text = { Text(descripcionSeleccionada) },
                confirmButton = {
                    TextButton(onClick = { mostrarDetalle = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(azul)
                .padding(16.dp)
        ) {
            TextButton(onClick = volver) {
                Text("‹ Volver", color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Material Complementario",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

        TabRow(
            selectedTabIndex = pestana,
            containerColor = Color.White
        ) {
            Tab(
                selected = pestana == 0,
                onClick = { pestana = 0 },
                text = { Text("Jurisprudencia") }
            )
            Tab(
                selected = pestana == 1,
                onClick = { pestana = 1 },
                text = { Text("Doctrina") }
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
                TarjetaMaterial(
                    "Corte Suprema - Rol 123-2022",
                    "Ejemplo de jurisprudencia sobre interpretación de normas y consentimiento.",
                    onClick = {
                        tituloSeleccionado = "Corte Suprema - Rol 123-2022"
                        descripcionSeleccionada = "Ejemplo de jurisprudencia sobre interpretación de normas y consentimiento."
                        mostrarDetalle = true
                    }
                )
            } else {
                TarjetaMaterial(
                    "Tratado de Derecho Civil - Alessandri",
                    "Análisis doctrinal sobre los elementos de la obligación.",
                    onClick = {
                        tituloSeleccionado = "Tratado de Derecho Civil"
                        descripcionSeleccionada = "Análisis doctrinal sobre los elementos de la obligación."
                        mostrarDetalle = true
                    }
                )
            }
        }
    }
}

@Composable
fun TarjetaMaterial(
    titulo: String,
    descripcion: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = titulo,
                style = MaterialTheme.typography.titleSmall,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall,
                color = Color.DarkGray
            )
        }
    }
}
