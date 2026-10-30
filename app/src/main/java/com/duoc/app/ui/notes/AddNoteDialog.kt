package com.duoc.app.ui.notes

import android.Manifest
import android.graphics.Bitmap
import android.util.Base64
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AddNoteDialog(
    onDismiss: () -> Unit,
    onSaveNote: (title: String, content: String, reference: String, date: String, imageBase64: String?) -> Unit
) {
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var reference by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var contentError by remember { mutableStateOf(false) }

    var capturedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var imageBase64String by remember { mutableStateOf<String?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            capturedBitmap = bitmap
            imageBase64String = encodeBitmapToBase64(bitmap)
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(
                context,
                "Permiso de cámara denegado. No se puede adjuntar la foto.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Nueva Nota de Estudio") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                OutlinedTextField(
                    value = title,
                    onValueChange = {
                        title = it
                        if (it.isNotBlank()) titleError = false
                    },
                    label = { Text("Título *") },
                    isError = titleError,
                    supportingText = { if (titleError) Text("El título es obligatorio") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = reference,
                    onValueChange = { reference = it },
                    label = { Text("Referencia (Ej: Art. 1450 CC)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = content,
                    onValueChange = {
                        content = it
                        if (it.isNotBlank()) contentError = false
                    },
                    label = { Text("Apunte *") },
                    isError = contentError,
                    supportingText = { if (contentError) Text("El contenido es obligatorio") },
                    minLines = 3,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedButton(
                    onClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("📷 Adjuntar Foto con Cámara")
                }

                capturedBitmap?.let { bmp ->
                    Image(
                        bitmap = bmp.asImageBitmap(),
                        contentDescription = "Foto capturada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    var valid = true
                    if (title.isBlank()) {
                        titleError = true
                        valid = false
                    }
                    if (content.isBlank()) {
                        contentError = true
                        valid = false
                    }

                    if (valid) {
                        val currentDate = SimpleDateFormat(
                            "dd/MM/yyyy HH:mm",
                            Locale.getDefault()
                        ).format(Date())

                        val refText = if (reference.isBlank()) "General" else reference

                        onSaveNote(
                            title.trim(),
                            content.trim(),
                            refText,
                            currentDate,
                            imageBase64String
                        )

                        onDismiss()
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

private fun encodeBitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
}