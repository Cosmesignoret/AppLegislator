<<<<<<< HEAD:app/src/main/java/com/duoc/app/ui/personalnotes/PersonalNotesScreen.kt
package com.duoc.app.ui.personalnotes
=======

package com.duoc.app
>>>>>>> a484bad (se creo la validacion de correo y contraseña, tambien para guardarlo y mantenerlo):app/src/main/java/com/duoc/app/PersonalNotesScreen.kt

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.duoc.app.AddNoteDialog
import com.duoc.app.data.model.NoteEntity
import com.duoc.app.viewmodel.NoteViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonalNotesScreen(
    viewModel: NoteViewModel,
    onBack: () -> Unit
) {
    val notes by viewModel.notesState.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var noteToDelete by remember { mutableStateOf<NoteEntity?>(null) }

    var fabPressed by remember { mutableStateOf(false) }
    val fabScale by animateFloatAsState(
        targetValue = if (fabPressed) 1.2f else 1.0f,
        label = "fabScale"
    )

    val azul = Color(0xFF205C95)
    val fondo = Color(0xFFEDF5FA)
    val texto = Color(0xFF17232F)
    val textoSecundario = Color(0xFF607080)

    Scaffold(
        containerColor = fondo,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis Notas de Estudio",
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    fabPressed = true
                    showAddDialog = true
                },
                modifier = Modifier.scale(fabScale),
                containerColor = azul,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Nueva Nota"
                )
            }
        }
    ) { innerPadding ->

        LaunchedEffect(fabPressed) {
            if (fabPressed) {
                delay(150)
                fabPressed = false
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(fondo)
        ) {
            AnimatedVisibility(
                visible = notes.isEmpty(),
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "No tienes notas guardadas.\nPresiona '+' para agregar una.",
                    color = textoSecundario
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(notes, key = { it.id }) { note ->
                    NoteCard(
                        note = note,
                        onDelete = { noteToDelete = note }
                    )
                }
            }
        }

        if (showAddDialog) {
            AddNoteDialog(
                onDismiss = { showAddDialog = false },
                onSaveNote = { title, content, reference, date, imageBase64 ->
                    viewModel.addNote(
                        title,
                        content,
                        reference,
                        date,
                        imageBase64
                    )
                }
            )
        }

        noteToDelete?.let { note ->
            AlertDialog(
                onDismissRequest = { noteToDelete = null },
                title = {
                    Text(
                        text = "Eliminar Nota",
                        color = azul
                    )
                },
                text = {
                    Text(
                        text = "¿Estás seguro de eliminar '${note.title}'?",
                        color = texto
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteNote(note)
                            noteToDelete = null
                        }
                    ) {
                        Text(
                            text = "Eliminar",
                            color = Color(0xFFD32F2F)
                        )
                    }
                },
                dismissButton = {
                    TextButton(onClick = { noteToDelete = null }) {
                        Text(
                            text = "Cancelar",
                            color = azul
                        )
                    }
                },
                containerColor = Color.White
            )
        }
    }
}

@Composable
fun NoteCard(
    note: NoteEntity,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = note.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF17232F)
                )

                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }

            Text(
                text = note.legalReference,
                fontSize = 12.sp,
                color = Color(0xFF205C95)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = note.content,
                fontSize = 14.sp,
                color = Color(0xFF17232F)
            )

            note.imageBase64?.let { base64 ->
                val bitmap = remember(base64) {
                    try {
                        val bytes = Base64.decode(base64, Base64.DEFAULT)
                        BitmapFactory.decodeByteArray(
                            bytes,
                            0,
                            bytes.size
                        )
                    } catch (e: Exception) {
                        null
                    }
                }

                bitmap?.let { bmp ->
                    Spacer(modifier = Modifier.height(8.dp))

                    Image(
                        bitmap = bmp.asImageBitmap(),
                        contentDescription = "Foto guardada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                    )
                }
            }
        }
    }
}
