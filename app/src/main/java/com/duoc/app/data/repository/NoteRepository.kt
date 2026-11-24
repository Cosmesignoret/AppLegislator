package com.duoc.app.data.repository

import com.duoc.app.data.model.NoteDao
import com.duoc.app.data.model.NoteEntity
import com.duoc.app.data.remote.api.NoteApi
import com.duoc.app.data.remote.dto.CreateNoteRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart

class NoteRepository(
    private val noteDao: NoteDao,
    private val noteApi: NoteApi
) {

    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()
        .onStart { syncWithServer() }

    suspend fun syncWithServer() {
        try {
            // Subir notas creadas offline pendientes
            val pending = noteDao.getUnsyncedNotes()
            for (local in pending) {
                val created = noteApi.createNote(
                    CreateNoteRequest(
                        title = local.title,
                        content = local.content,
                        legalReference = local.legalReference,
                        dateText = local.dateText,
                        imageBase64 = local.imageBase64
                    )
                )
                noteDao.updateNote(local.copy(remoteId = created.id, isSynced = true))
            }

            // Descarga estado actual del servidor y actualizar caché local
            val remoteNotes = noteApi.getNotes()
            noteDao.deleteAllSynced()
            for (dto in remoteNotes) {
                noteDao.insertNote(
                    NoteEntity(
                        remoteId = dto.id,
                        isSynced = true,
                        title = dto.title,
                        content = dto.content,
                        legalReference = dto.legalReference,
                        dateText = dto.dateText,
                        imageBase64 = dto.imageBase64
                    )
                )
            }
        } catch (_: Exception) {
            // si no hay conexion Room sigue operando con los datos locales
        }
    }

    suspend fun insert(note: NoteEntity) {
        val localId = noteDao.insertNote(note.copy(isSynced = false)).toInt()
        try {
            val created = noteApi.createNote(
                CreateNoteRequest(
                    title = note.title,
                    content = note.content,
                    legalReference = note.legalReference,
                    dateText = note.dateText,
                    imageBase64 = note.imageBase64
                )
            )
            noteDao.updateNote(
                note.copy(
                    id = localId,
                    remoteId = created.id,
                    isSynced = true
                )
            )
        } catch (_: Exception) {
            // Queda marcada con isSynced = false para subirse en la próxima sincronización
        }
    }

    suspend fun delete(note: NoteEntity) {
        noteDao.deleteNote(note)
        val remoteId = note.remoteId ?: return
        try {
            noteApi.deleteNote(remoteId)
        } catch (_: Exception) {
            // Ya eliminada localmente
        }
    }
}