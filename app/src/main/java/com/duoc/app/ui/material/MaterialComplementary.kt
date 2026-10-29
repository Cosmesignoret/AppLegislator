package com.duoc.app.ui.material

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                title = {
                    Text(tituloSeleccionado)
                },
                text = {
                    Text(descripcionSeleccionada)
                },
                confirmButton = {
                    TextButton(
                        onClick = { mostrarDetalle = false }
                    ) {
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
                Text("‹", color = Color.White)
            }
            Text(
                text = "Material Complementario",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp)
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
                        descripcionSeleccionada =
                            "Ejemplo de jurisprudencia sobre interpretación de normas y consentimiento."
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