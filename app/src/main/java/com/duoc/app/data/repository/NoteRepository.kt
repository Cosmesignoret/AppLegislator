package com.duoc.app.data.repository

import com.duoc.app.data.model.NoteDao
import com.duoc.app.data.model.NoteEntity
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: Flow<List<NoteEntity>> = noteDao.getAllNotes()

    suspend fun insert(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    suspend fun delete(note: NoteEntity) {
        noteDao.deleteNote(note)
    }
}
